package swyg.hollang.manager

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.*
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
internal class RecommendationManagerTest(
    @Autowired val em: EntityManager,
    @Autowired val testResponseManager: TestResponseManager,
    @Autowired val recommendationManager: RecommendationManager
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

        createHobbiesAndHobbyType()
    }

    @Test
    fun getRecommendationById() {
        // given
        val createUserRequest = CreateUserRequest("쨈")
        val createTestResponseDetailRequests = listOf(
            CreateTestResponseDetailRequest(1, 2),
            CreateTestResponseDetailRequest(2, 2),
            CreateTestResponseDetailRequest(3, 2),
            CreateTestResponseDetailRequest(4, 2),
            CreateTestResponseDetailRequest(5, 1),
            CreateTestResponseDetailRequest(6, 1),
            CreateTestResponseDetailRequest(7, 2),
            CreateTestResponseDetailRequest(8, 1),
            CreateTestResponseDetailRequest(9, 2),
            CreateTestResponseDetailRequest(10, 1),
            CreateTestResponseDetailRequest(11, 2),
            CreateTestResponseDetailRequest(12, 2)
        )
        val createTestResponseRequest = CreateTestResponseRequest(createUserRequest, createTestResponseDetailRequests)
        val createTestResponseResponse =
            testResponseManager.createTestResponse(createTestResponseRequest = createTestResponseRequest)

        // when
        val recommendationResponse =
            recommendationManager.getRecommendationById(createTestResponseResponse.recommendation.id)

        // then
        Assertions.assertThat(recommendationResponse.recommendation.id)
            .isEqualTo(createTestResponseResponse.recommendation.id)

    }

    @Test
    fun getRecommendationWithoutFitHobbiesById() {
        // given
        val createUserRequest = CreateUserRequest("쨈")
        val createTestResponseDetailRequests = listOf(
            CreateTestResponseDetailRequest(1, 2),
            CreateTestResponseDetailRequest(2, 2),
            CreateTestResponseDetailRequest(3, 2),
            CreateTestResponseDetailRequest(4, 2),
            CreateTestResponseDetailRequest(5, 1),
            CreateTestResponseDetailRequest(6, 1),
            CreateTestResponseDetailRequest(7, 2),
            CreateTestResponseDetailRequest(8, 1),
            CreateTestResponseDetailRequest(9, 2),
            CreateTestResponseDetailRequest(10, 1),
            CreateTestResponseDetailRequest(11, 2),
            CreateTestResponseDetailRequest(12, 2)
        )
        val createTestResponseRequest = CreateTestResponseRequest(createUserRequest, createTestResponseDetailRequests)
        val createTestResponseResponse =
            testResponseManager.createTestResponse(createTestResponseRequest = createTestResponseRequest)

        // when
        val recommendationResponse =
            recommendationManager.getRecommendationWithoutFitHobbiesById(createTestResponseResponse.recommendation.id)

        // then
        Assertions.assertThat(recommendationResponse.recommendation.id)
            .isEqualTo(createTestResponseResponse.recommendation.id)
    }

    @Test
    fun createRecommendationSurvey() {
        // given
        val createUserRequest = CreateUserRequest("쨈")
        val createTestResponseDetailRequests = listOf(
            CreateTestResponseDetailRequest(1, 2),
            CreateTestResponseDetailRequest(2, 2),
            CreateTestResponseDetailRequest(3, 2),
            CreateTestResponseDetailRequest(4, 2),
            CreateTestResponseDetailRequest(5, 1),
            CreateTestResponseDetailRequest(6, 1),
            CreateTestResponseDetailRequest(7, 2),
            CreateTestResponseDetailRequest(8, 1),
            CreateTestResponseDetailRequest(9, 2),
            CreateTestResponseDetailRequest(10, 1),
            CreateTestResponseDetailRequest(11, 2),
            CreateTestResponseDetailRequest(12, 2)
        )
        val createTestResponseRequest = CreateTestResponseRequest(createUserRequest, createTestResponseDetailRequests)
        val createTestResponseResponse =
            testResponseManager.createTestResponse(createTestResponseRequest = createTestResponseRequest)

        val recommendationResponse =
            recommendationManager.getRecommendationById(recommendationId = createTestResponseResponse.recommendation.id)
        val hobbyDtos = recommendationResponse.recommendation.hobbies.map { hobby ->
            HobbyDto(hobby.id, hobby.originalName, 1)
        }

        // when, then
        val createRecommendationSurveyRequest = CreateRecommendationSurveyRequest(CreateSurveyRequest(hobbyDtos))
        recommendationManager
            .createRecommendationSurvey(createTestResponseResponse.recommendation.id, createRecommendationSurveyRequest)
    }

    private fun createFitHobbyTypes(mbtiTypes: List<String>): MutableSet<FitHobbyType> {
        return mutableSetOf(
            FitHobbyType(mbtiTypes[0], 1),
            FitHobbyType(mbtiTypes[1], 2),
            FitHobbyType(mbtiTypes[2], 3)
        )
    }

    private fun createHobbiesAndHobbyType() {

        val fitHobbyTypes = createFitHobbyTypes(listOf("ESTJ", "ISFP", "ISTP"))
        val hobbyType = HobbyType(
            "나를 따르라형",
            "너도나도 팀이 되고 싶어 하는\\n마성의 매력 소유자!\\n숨길 수 없는 당신의 인싸력으로\\n취미를 짜릿하게 즐겨보아요",
            "https://example.com",
            "ESFJ",
            fitHobbyTypes
        )
        val fitHobbyTypes1 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType1 = HobbyType(
            "무계획은 못참아형", "취미 유형 상세정보", "https://example.com", "ESTJ", fitHobbyTypes1
        )
        val fitHobbyTypes2 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType2 = HobbyType(
            "키다리 아저씨형", "취미 유형 상세정보", "https://example.com", "ISFP", fitHobbyTypes2
        )
        val fitHobbyTypes3 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType3 = HobbyType(
            "재주 많은 뚝딱형", "취미 유형 상세정보", "https://example.com", "ISTP", fitHobbyTypes3
        )
        em.persist(hobbyType)
        em.persist(hobbyType1)
        em.persist(hobbyType2)
        em.persist(hobbyType3)

        val hobby1 = Hobby("Adobe illustrator로 나만의 굿즈 만들기", "어도비 일러스트레이트",
            "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby2 = Hobby("사람들을 사로잡는 스토리텔링 시작하기", "스토리텔링",
            "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby3 = Hobby("Adobe InDesign으로 편집 디자인 시작하기", "어도비 인디자인",
            "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)
    }
}
