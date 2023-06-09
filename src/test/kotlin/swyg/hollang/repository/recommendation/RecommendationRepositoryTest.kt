package swyg.hollang.repository.recommendation

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import swyg.hollang.repository.answer.AnswerRepository
import swyg.hollang.repository.hobby.HobbyRepository
import swyg.hollang.repository.hobbytype.HobbyTypeRepository
import swyg.hollang.repository.testresponse.TestResponseRepository

@ActiveProfiles(value = ["test"])
@SpringBootTest
@Transactional
internal class RecommendationRepositoryTest(
    @Autowired val em: EntityManager,
    @Autowired val hobbyRepository: HobbyRepository,
    @Autowired val hobbyTypeRepository: HobbyTypeRepository,
    @Autowired val answerRepository: AnswerRepository,
    @Autowired val testResponseRepository: TestResponseRepository,
    @Autowired val recommendationRepository: RecommendationRepository) {

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
    fun findWithUserAndHobbyTypeAndHobbiesAndSurveyById() {
        // given
        val (recommendation, savedTestResponse) = createTestResponse()

        // when
        val findRecommendation = recommendationRepository
                .findWithUserAndHobbyTypeAndHobbiesAndSurveyById(savedTestResponse.recommendation?.id!!)

        // then
        assertThat(findRecommendation.recommendationHobbies[0].hobby)
            .isEqualTo(recommendation.recommendationHobbies[0].hobby)
    }

    @Test
    fun findWithUserAndHobbyTypeAndHobbiesById() {
        // given
        val (recommendation, savedTestResponse) = createTestResponse()

        // when
        val findRecommendation = recommendationRepository
                .findWithUserAndHobbyTypeAndHobbiesById(savedTestResponse.recommendation?.id!!)

        // then
        assertThat(findRecommendation.recommendationHobbies[0].hobby)
            .isEqualTo(recommendation.recommendationHobbies[0].hobby)
    }

    @Test
    fun findWithHobbiesById() {
        // given
        val (recommendation, savedTestResponse) = createTestResponse()

        // when
        val findRecommendation = recommendationRepository
            .findWithHobbiesById(savedTestResponse.recommendation?.id!!)

        // then
        assertThat(findRecommendation.recommendationHobbies[0].hobby)
            .isEqualTo(recommendation.recommendationHobbies[0].hobby)
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
        val answers = answerRepository
            .findAllByQuestionAnswerPairsAndTestVersion(questionAnswerPairs, 1)
        val testResponseDetails: MutableList<TestResponseDetail> = answers.map { answer ->
            TestResponseDetail(answer)
        } as MutableList<TestResponseDetail>

        val fitHobbyTypes = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType = HobbyType(
            "취미 유형1", "취미 유형 상세정보", "https://example.com", "INTJ", fitHobbyTypes
        )
        hobbyTypeRepository.save(hobbyType)

        val hobby1 = Hobby("취미1", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby2 = Hobby("취미2", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby3 = Hobby("취미3", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobbyRepository.save(hobby1)
        hobbyRepository.save(hobby2)
        hobbyRepository.save(hobby3)

        val recommendationHobbies = mutableListOf(
            RecommendationHobby(hobby1, 1),
            RecommendationHobby(hobby2, 2),
            RecommendationHobby(hobby3, 3)
        )
        val mbtiScore = listOf(mapOf("scoreE" to 2), mapOf("scoreN" to 3), mapOf("scoreF" to 1), mapOf("scoreJ" to 2))
        val recommendation = Recommendation(hobbyType, mbtiScore, recommendationHobbies)

        val savedTestResponse = testResponseRepository.save(TestResponse(user, testResponseDetails, recommendation))
        return Pair(recommendation, savedTestResponse)
    }
}
