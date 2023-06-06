package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question
import swyg.hollang.repository.test.TestRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testRepository: TestRepository
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

        val test = swyg.hollang.entity.Test(1, questions)
        em.persist(test)
    }

    @Test
    fun findByVersionWithQuestions() {
        //given
        val validVersion: Long = 1
        val invalidVersion: Long = 2

        //when
        val validTest = testRepository.findByVersionWithQuestions(validVersion)

        //then
        assertThat(validTest.questions.size).isSameAs(12)

        assertThatThrownBy { testRepository.findByVersionWithQuestions(invalidVersion) }
            .isExactlyInstanceOf(JpaObjectRetrievalFailureException::class.java)
    }

}
