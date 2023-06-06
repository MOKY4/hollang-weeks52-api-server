package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testService: TestService) {

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
    fun findAndShuffleTestByVersion() {
        //given
        val validVersion: Long = 1
        val invalidVersion: Long = 2

        //when
        val testResponse = testService.findAndShuffleTestByVersion(validVersion)

        //then
        assertThat(testResponse.test.questions.size).isSameAs(12)

        assertThatThrownBy { testService.findAndShuffleTestByVersion(invalidVersion) }
            .isExactlyInstanceOf(JpaObjectRetrievalFailureException::class.java)
    }
}
