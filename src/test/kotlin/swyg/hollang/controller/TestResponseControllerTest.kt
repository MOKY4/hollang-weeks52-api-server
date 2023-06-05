package swyg.hollang.controller

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class TestResponseControllerTest(
    @Autowired private val em: EntityManager,
    @Autowired private val mockMvc: MockMvc
) {

    @BeforeEach
    fun beforeEach() {
        val questions: MutableSet<Question> = mutableSetOf()
        for(i in 1..12){
            val answers: MutableSet<Answer> = mutableSetOf()
            for(j in 1..2){
                val answer = Answer(j.toLong(), "질문 $i 답변 $j")
                answers.add(answer)
            }

            val question = Question(i.toLong(),"질문 $i", answers)
            questions.add(question)
        }

        val test = Test(1, questions)
        em.persist(test)

        val hobby1 = Hobby(
            "어도비 일러스트레이트",
            "default",
            "어도비 일러스트레이트 상세정보",
            "https://example.com/hollang.png"
        )
        val hobby2 = Hobby(
            "일러스트 정복하기",
            "default",
            "일러스트 정복하기 상세정보",
            "https://example.com/hollang.png"
        )
        val hobby3 = Hobby(
            "어도비 인디자인",
            "default",
            "어도비 인디자인 상세정보",
            "https://example.com/hollang.png"
        )
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)
    }

    @Test
    fun createTestResponse(){

        val requestBody: String = "{\n" +
                "    \"user\": {\n" +
                "        \"name\": \"쨈\"\n" +
                "    },\n" +
                "    \"testResponseDetail\": [\n" +
                "        {\n" +
                "            \"questionNumber\": 1,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 2,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 3,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 4,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 5,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 6,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 7,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 8,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 9,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 10,\n" +
                "            \"answerNumber\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 11,\n" +
                "            \"answerNumber\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionNumber\": 12,\n" +
                "            \"answerNumber\": 1\n" +
                "        }\n" +
                "    ]\n" +
                "}"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/test-responses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
    }

    @Test
    fun countAllTestResponse(){

        //given
        val testCount = 10L
        for(i in 1..testCount){
            val createdUser = User("쨈$i")
            em.persist(createdUser)
            val createdTestResponse = TestResponse(createdUser)
            em.persist(createdTestResponse)
        }

        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/test-responses/count")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.content().json("{\n" +
                    "    \"code\": \"OK\",\n" +
                    "    \"message\": \"요청이 성공하였습니다.\",\n" +
                    "    \"data\": {\n" +
                    "        \"testResponse\": {\n" +
                    "            \"count\": 10\n" +
                    "        }\n" +
                    "    }\n" +
                    "}"))
    }
}
