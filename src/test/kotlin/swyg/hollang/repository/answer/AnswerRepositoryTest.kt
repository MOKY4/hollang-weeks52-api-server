package swyg.hollang.repository.answer

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

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
internal class AnswerRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val answerRepository: AnswerRepository) {

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
    fun findByQuestionNumberWithTestVersion() {
        // given
        val questionAnswerPairs =
            listOf(1 to 2, 2 to 1, 3 to 2, 4 to 1, 5 to 1, 6 to 1, 7 to 1, 8 to 2, 9 to 1, 10 to 2, 11 to 1, 12 to 2)
        val testVersion = 1

        //when
        val findAnswers =
            answerRepository.findAllByQuestionAnswerPairsAndTestVersion(questionAnswerPairs, testVersion)

        //then
        assertThat(findAnswers.size).isSameAs(12)
    }
}
