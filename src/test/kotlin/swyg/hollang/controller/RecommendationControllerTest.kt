package swyg.hollang.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.*
import swyg.hollang.entity.*
import swyg.hollang.manager.RecommendationManager
import swyg.hollang.manager.TestResponseManager

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
internal class RecommendationControllerTest(
    @Autowired val em: EntityManager,
    @Autowired val mockMvc: MockMvc,
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
    fun getRecommendationDetails() {
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

        /**
         * when
         */
        val resultActions = mockMvc.perform(
            get("/recommendations/${createTestResponseResponse.recommendation.id}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        resultActions
            .andExpect(jsonPath("$.data.recommendation.id").exists())
            .andExpect(jsonPath("$.data.recommendation.didSurvey").exists())
            .andExpect(jsonPath("$.data.recommendation.user.id").exists())
            .andExpect(jsonPath("$.data.recommendation.user.name").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.id").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.name").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.description").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.imageUrl").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies.length()", 3).exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].id").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].shortName").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].summary").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].description").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].imageUrl").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].contentUrl").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].ranking").exists())
            .andExpect(jsonPath("$.data.recommendation.fitHobbyTypes.length()", 3).exists())
            .andExpect(jsonPath("$.data.recommendation.fitHobbyTypes[0].id").exists())
            .andExpect(jsonPath("$.data.recommendation.fitHobbyTypes[0].name").exists())
            .andExpect(jsonPath("$.data.recommendation.fitHobbyTypes[0].description").exists())
            .andExpect(jsonPath("$.data.recommendation.fitHobbyTypes[0].imageUrl").exists())
            .andExpect(jsonPath("$.data.recommendation.fitHobbyTypes[0].ranking").exists())

    }

    @Test
    fun getRecommendationShareDetails() {
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

        /**
         * when
         */
        val resultActions = mockMvc.perform(
            get("/recommendations/${createTestResponseResponse.recommendation.id}/shares")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        resultActions
            .andExpect(jsonPath("$.data.recommendation.id").exists())
            .andExpect(jsonPath("$.data.recommendation.user.id").exists())
            .andExpect(jsonPath("$.data.recommendation.user.name").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.id").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.name").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.description").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbyType.imageUrl").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies.length()", 3).exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].id").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].shortName").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].summary").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].description").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].imageUrl").exists())
            .andExpect(jsonPath("$.data.recommendation.hobbies[0].ranking").exists())
    }

    @Test
    fun addRecommendationSurveys() {
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
        val createRecommendationSurveyRequest = CreateRecommendationSurveyRequest(CreateSurveyRequest(hobbyDtos))

        val objectMapper = ObjectMapper()
        val requestBody = objectMapper.writeValueAsString(createRecommendationSurveyRequest)

        /**
         * when, then
         */
        mockMvc.perform(post("/recommendations/${createTestResponseResponse.recommendation.id}/surveys")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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

        val hobby1 = Hobby("Adobe illustrator로 나만의 굿즈 만들기", "어도비 일러스트레이트", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby2 = Hobby("사람들을 사로잡는 스토리텔링 시작하기", "스토리텔링", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby3 = Hobby("Adobe InDesign으로 편집 디자인 시작하기", "어도비 인디자인", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)
    }
}
