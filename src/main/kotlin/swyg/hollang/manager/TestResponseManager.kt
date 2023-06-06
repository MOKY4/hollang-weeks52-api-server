package swyg.hollang.manager

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.dto.CreateTestResponseResponse
import swyg.hollang.entity.*
import swyg.hollang.service.*

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
        val user = User(createTestResponseRequest.createUserRequest.name)

        //테스트 응답 상세정보 엔티티 생성
        val questionAnswerPairs = createTestResponseRequest
            .createTestResponseDetailRequests.map { it.questionNumber to it.answerNumber }
        val answers = answerService
            .getAnswersByQuestionAnswerPairsByTestVersion(questionAnswerPairs, 1)
        val testResponseDetails: MutableList<TestResponseDetail> = answers.map{answer ->
            TestResponseDetail(answer)
        } as MutableList<TestResponseDetail>

        //추론 서버에 추론 요청
        val createRecommendationResultResponse =
            inferringService.inferHobbiesAndType(createTestResponseRequest.createTestResponseDetailRequests)

        //추천받은 취미들의 추천수 카운트 증가
        val hobbies = hobbyService.addHobbiesRecommendCount(createRecommendationResultResponse.hobbies)

        //추천 취미 엔티티 생성
        val recommendationHobbies = hobbies.map { hobby ->
            RecommendationHobby(hobby)
        } as MutableList<RecommendationHobby>

        //추천받은 취미 유형 찾기
        val mbtiType = createRecommendationResultResponse.hobbyType["mbtiType"] as String
        val hobbyType =
            hobbyTypeService.getHobbyTypeByMbtiType(mbtiType)

        //추천 엔티티 생성
        val mbtiScore = createRecommendationResultResponse.hobbyType["scores"] as List<Map<String, Int>>
        val recommendation = Recommendation(hobbyType, mbtiScore, recommendationHobbies)

        // 테스트 응답 엔티티 생성
        val testResponse = testResponseService
            .createTestResponse(TestResponse(user, testResponseDetails, recommendation))

        return CreateTestResponseResponse(testResponse)
    }
}
