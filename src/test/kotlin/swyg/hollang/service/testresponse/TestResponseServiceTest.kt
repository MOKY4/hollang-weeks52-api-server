package swyg.hollang.service.testresponse

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
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

@ActiveProfiles(value = ["test"])
@SpringBootTest
@Transactional
internal class TestResponseServiceTest(
    @Autowired val em: EntityManager,
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
    @DisplayName("테스트 응답 생성")
    fun create() {
        // given
        val testResponse = createTestResponse()

        // when
        val savedTestResponse = testResponseService.create(testResponse = testResponse)

        // then
        Assertions.assertThat(savedTestResponse.user.name).isEqualTo(testResponse.user.name)
    }

    @Test
    @DisplayName("테스트 응답 총 횟수 조회")
    fun countAll() {
        // given
        for(i in 1..10) {
            val testResponse = createTestResponse()
            testResponseService.create(testResponse)
        }

        // when
        val testResponseCount = testResponseService.countAll()

        // then
        Assertions.assertThat(testResponseCount).isSameAs(10L)
    }

    private fun createFitHobbyTypes(mbtiTypes: List<String>): MutableSet<FitHobbyType> {
        return mutableSetOf(
            FitHobbyType(mbtiTypes[0], 1),
            FitHobbyType(mbtiTypes[1], 2),
            FitHobbyType(mbtiTypes[2], 3)
        )
    }

    private fun createTestResponse(): TestResponse {
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

        val hobby1 = Hobby("취미1", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby2 = Hobby("취미2", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby3 = Hobby("취미3", "취미 요약정보", "취미 상세정보", "https://example.com")
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

        val testResponse = TestResponse(user, testResponseDetails, recommendation)
        return testResponse
    }
}
