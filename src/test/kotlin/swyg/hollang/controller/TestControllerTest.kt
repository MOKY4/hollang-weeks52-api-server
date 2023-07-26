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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class TestControllerTest(
    @Autowired val em: EntityManager,
    @Autowired val mockMvc: MockMvc) {

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

        val test = swyg.hollang.entity.Test(1, questions)
        em.persist(test)
    }

    @Test
    fun getTestDetails() {
        //given
        val endpoint = "/tests"
        val validVersion: Long = 1
        val invalidVersion: Long = 2

        /**
         * when
          */

        // 유효한 버전 정보를 넣었을때
        val resultActions1 = mockMvc.perform(get(endpoint)
            .queryParam("version", validVersion.toString()))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        // 버전 정보를 넣지 않았을때
        val resultActions2 = mockMvc.perform(get(endpoint))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        // 유효하지 않은 버전 정보를 넣었을때
        mockMvc.perform(
            get(endpoint)
                .queryParam("version", invalidVersion.toString()))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        // 유효하지 않은 HTTP Method로 요청하였을 때
        mockMvc.perform(
            post(endpoint)
                .queryParam("version", invalidVersion.toString()))
            .andExpect(status().isMethodNotAllowed)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        /**
         * then
          */

        // 버전 정보를 넣지 않았을때
        resultActions2
            .andExpect(jsonPath("$.data.test").exists())
            .andExpect(jsonPath("$.data.test.questions.length()", 12).exists())
            .andExpect(jsonPath("$.data.test.questions[0].answers.length()", 2).exists())

        // 유효한 버전 정보를 넣었을때
        resultActions1
            .andExpect(jsonPath("$.data.test").exists())
            .andExpect(jsonPath("$.data.test.questions.length()", 12).exists())
            .andExpect(jsonPath("$.data.test.questions[0].answers.length()", 2).exists())
    }

}
