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
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.dto.CreateUserRequest
import swyg.hollang.entity.*
import swyg.hollang.manager.TestResponseManager

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class TestResponseControllerTest(
    @Autowired val em: EntityManager,
    @Autowired val mockMvc: MockMvc,
    @Autowired val testResponseManager: TestResponseManager
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
    fun createTestResponse(){
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

        val objectMapper = ObjectMapper()
        val requestBody = objectMapper.writeValueAsString(createTestResponseRequest)

        /**
         * when
          */

        val resultActions = mockMvc.perform(post("/test-responses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        /**
         * then
         */
        resultActions
            .andExpect(jsonPath("$..['user']").exists())
            .andExpect(jsonPath("$..['recommendation']").exists())
    }

    @Test
    fun countAllTestResponse(){

        //given
        val testCount = 5L
        for(i in 1..testCount) {
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

            testResponseManager.createTestResponse(createTestResponseRequest = createTestResponseRequest)
        }

        /**
         * when
         */
        val resultActions = mockMvc.perform(
            get("/test-responses/count")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        /**
         * then
         */
        resultActions
            .andExpect(jsonPath("$.data.testResponse").exists())
            .andExpect(jsonPath("$.data.testResponse.count", 5).exists())
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
        em.persist(hobbyType)

        val hobby1 = Hobby("어도비 일러스트레이트", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby2 = Hobby("스토리텔링", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby3 = Hobby("어도비 인디자인", "취미 요약정보", "취미 상세정보", "https://example.com")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)
    }
}
