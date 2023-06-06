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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class TestControllerTest(
    @Autowired private val em: EntityManager,
    @Autowired private val mockMvc: MockMvc) {

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

        val test = swyg.hollang.entity.Test(1, questions)
        em.persist(test)
    }

    @Test
    fun testDetailsWithSuccess() {
        //given
        val validVersion: Long = 1

        mockMvc.perform(
            get("/tests")
                .queryParam("version", validVersion.toString()))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

    }

    @Test
    fun testDetailsWithException() {
        //given
        val invalidVersion: Long = 2

        mockMvc.perform(
            get("/tests")
                .queryParam("version", invalidVersion.toString()))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    }
}
