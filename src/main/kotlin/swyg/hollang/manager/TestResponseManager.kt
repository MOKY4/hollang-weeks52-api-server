package swyg.hollang.manager

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.dto.CreateTestResponseResponse
import swyg.hollang.dto.GetInferringResultResponse
import swyg.hollang.entity.*
import swyg.hollang.service.answer.AnswerService
import swyg.hollang.service.hobby.HobbyService
import swyg.hollang.service.hobbytype.HobbyTypeService
import swyg.hollang.service.inferring.InferringService
import swyg.hollang.service.testresponse.TestResponseService

@Component
class TestResponseManager(
    private val testResponseService: TestResponseService,
    private val answerService: AnswerService,
    private val inferringService: InferringService,
    private val hobbyService: HobbyService,
    private val hobbyTypeService: HobbyTypeService
) {

    @Transactional
    fun createTestResponse(createTestResponseRequest: CreateTestResponseRequest): CreateTestResponseResponse {
        //유저 엔티티 생성
        val user = User(name = createTestResponseRequest.user.name)

        //테스트 응답 상세정보 엔티티 생성
        val testResponseDetails: MutableList<TestResponseDetail> = createTestResponseDetails(
            createTestResponseDetailRequests = createTestResponseRequest.testResponseDetails)

        //추론 서버에 추론 요청
        val getInferringResultResponse = inferringService.inferByQuestionAnswerPairs(
                createTestResponseDetailRequests = createTestResponseRequest.testResponseDetails)

        //추론한 취미들의 추천수 카운트 증가
        val hobbyNames = extractHobbyNames(hobbies = getInferringResultResponse.hobbies)
        val hobbies = hobbyService.getAllByOriginalNameIsIn(originalNames = hobbyNames)
        hobbyService.incrementRecommendCountByOriginalNameIsIn(originalNames = hobbyNames)

        //추천 취미 엔티티 생성
        val recommendationHobbies = createRecommendationHobbies(
            hobbies = hobbies, inferringHobbies = getInferringResultResponse.hobbies)

        //추천받은 취미 유형 찾기
        val mbtiType = extractMbtiType(getInferringResultResponse = getInferringResultResponse)
        val hobbyType = hobbyTypeService.getByMbtiType(mbtiType = mbtiType)

        //추천 엔티티 생성
        val mbtiScore = extractMbtiScore(getInferringResultResponse = getInferringResultResponse)
        val recommendation = Recommendation(
            hobbyType = hobbyType, mbtiScore = mbtiScore, recommendationHobbies = recommendationHobbies)

        // 테스트 응답 엔티티 생성
        val testResponse = testResponseService.create(
                TestResponse(user = user, testResponseDetails = testResponseDetails, recommendation = recommendation))

        return CreateTestResponseResponse(testResponseEntity = testResponse)
    }

    @Suppress("UNCHECKED_CAST")
    private fun extractMbtiScore(getInferringResultResponse: GetInferringResultResponse) =
        getInferringResultResponse.hobbyType["scores"] as List<Map<String, Int>>

    private fun extractMbtiType(getInferringResultResponse: GetInferringResultResponse) =
        getInferringResultResponse.hobbyType["mbtiType"] as String

    private fun createRecommendationHobbies(
        hobbies: List<Hobby>,
        inferringHobbies: MutableList<MutableMap<String, Any>>
    ): MutableList<RecommendationHobby> {
        val recommendationHobbies = hobbies.map { hobby ->
            val ranking = inferringHobbies.find { h ->
                h["name"]!! == hobby.originalName
            }?.get("ranking") as Int
            RecommendationHobby(hobby, ranking)
        } as MutableList<RecommendationHobby>
        return recommendationHobbies
    }

    private fun extractHobbyNames(hobbies: MutableList<MutableMap<String, Any>>): List<String> {
        val hobbyNames = hobbies.map { hobby ->
            val name = (hobby["name"] ?: "").toString()
            name.trim()
            name
        }
        return hobbyNames
    }

    private fun createTestResponseDetails(createTestResponseDetailRequests: List<CreateTestResponseDetailRequest>)
        : MutableList<TestResponseDetail> {
        val questionAnswerPairs = createTestResponseDetailRequests.map { it.questionNumber to it.answerNumber }
        val answers = answerService
            .getAllByQuestionAnswerPairsByTestVersion(questionAnswerPairs, 1)
        val testResponseDetails: MutableList<TestResponseDetail> = answers.map { answer ->
            TestResponseDetail(answer)
        } as MutableList<TestResponseDetail>
        return testResponseDetails
    }
}
