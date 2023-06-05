package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question
import swyg.hollang.repository.answer.AnswerRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class AnswerRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val answerRepository: AnswerRepository
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
    fun findByQuestionNumberWithTestVersion() {
        //given
        val questionAnswerPairs = listOf(Pair(1L, 2L))
        val testVersion: Long = 1

        //when
        val findAnswers =
            answerRepository.findAllByQuestionAnswerPairsByTestVersion(questionAnswerPairs, testVersion)

        //then
        assertThat(findAnswers[0].content).isEqualTo("질문 1 답변 2")
        assertThat(findAnswers[0].question!!.content).isEqualTo("질문 1")
    }
}
