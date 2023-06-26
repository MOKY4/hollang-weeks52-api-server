package swyg.hollang.service.recommendation

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import swyg.hollang.service.answer.AnswerService
import swyg.hollang.service.testresponse.TestResponseService

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
internal class RecommendationServiceTest(
    @Autowired val em: EntityManager,
    @Autowired val recommendationService: RecommendationService,
    @Autowired val answerService: AnswerService,
    @Autowired val testResponseService: TestResponseService
) {

    @BeforeEach
    fun beforeEach() {
        val questions: MutableSet<Question> = mutableSetOf()
        for(i in 1..12){
            val answers: MutableSet<Answer> = mutableSetOf()
            for(j in 1..2){
                val answer = Answer(j, "질문 $i 답변 $j")
                answers.add(answer)
            }

            val question = Question(i,"질문 $i", answers)
            questions.add(question)
        }
        val test = Test(1, questions)
        em.persist(test)
    }

    @Test
    @DisplayName("추천 ID로 추천과 연관된 엔티티 모두 조회")
    fun getWithUserAndHobbyTypeAndHobbiesAndSurveyById() {
        // given
        val (recommendation, testResponse) = createTestResponse()

        // when
        val findRecommendation =
            recommendationService.getWithUserAndHobbyTypeAndHobbiesAndSurveyById(testResponse.recommendation?.id!!)

        // then
        assertThat(findRecommendation.mbtiScore).isEqualTo(recommendation.mbtiScore)
    }

    @Test
    @DisplayName("추천 ID로 추천과 설문조사를 제외한 연관된 엔티티 모두 조회")
    fun getWithUserAndHobbyTypeAndHobbiesById() {
        // given
        val (recommendation, testResponse) = createTestResponse()

        // when
        val findRecommendation =
            recommendationService.getWithUserAndHobbyTypeAndHobbiesById(testResponse.recommendation?.id!!)

        // then
        assertThat(findRecommendation.mbtiScore).isEqualTo(recommendation.mbtiScore)
    }

    @Test
    @DisplayName("추천 ID로 추천과 추천 취미들 모두 조회")
    fun getWithHobbiesById() {
        // given
        val (recommendation, testResponse) = createTestResponse()

        // when
        val findRecommendation =
            recommendationService.getWithHobbiesById(testResponse.recommendation?.id!!)

        // then
        assertThat(findRecommendation.mbtiScore).isEqualTo(recommendation.mbtiScore)
    }

    private fun createFitHobbyTypes(mbtiTypes: List<String>): MutableSet<FitHobbyType> {
        return mutableSetOf(
            FitHobbyType(mbtiTypes[0], 1),
            FitHobbyType(mbtiTypes[1], 2),
            FitHobbyType(mbtiTypes[2], 3)
        )
    }

    private fun createTestResponse(): Pair<Recommendation, TestResponse> {
        val user = User("쨈")
        val questionAnswerPairs =
            listOf(1 to 2, 2 to 1, 3 to 2, 4 to 1, 5 to 1, 6 to 1, 7 to 1, 8 to 2, 9 to 1, 10 to 2, 11 to 1, 12 to 2)
        val answers = answerService
            .getAllByQuestionAnswerPairsByTestVersion(questionAnswerPairs, 1)
        val testResponseDetails: MutableList<TestResponseDetail> = answers.map { answer ->
            TestResponseDetail(answer)
        } as MutableList<TestResponseDetail>

        val fitHobbyTypes = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType = HobbyType(
            "취미 유형1", "취미 유형 상세정보", "https://example.com", "INTJ", fitHobbyTypes
        )
        em.persist(hobbyType)

        val hobby1 = Hobby("취미1", "취미1 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby2 = Hobby("취미2", "취미2 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby3 = Hobby("취미3", "취미3 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)

        val recommendationHobbies = mutableListOf(
            RecommendationHobby(hobby1, 1),
            RecommendationHobby(hobby2, 2),
            RecommendationHobby(hobby3, 3)
        )
        val mbtiScore = listOf(mapOf("scoreE" to 2), mapOf("scoreN" to 3), mapOf("scoreF" to 1), mapOf("scoreJ" to 2))
        val recommendation = Recommendation(hobbyType, mbtiScore, recommendationHobbies)

        val savedTestResponse = testResponseService.create(TestResponse(user, testResponseDetails, recommendation))
        return Pair(recommendation, savedTestResponse)
    }
}
