package swyg.hollang.service.answer

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
internal class AnswerServiceTest(
    @Autowired val em: EntityManager,
    @Autowired val answerService: AnswerService) {

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
    @DisplayName("테스트 버전과 질문 및 답변 쌍으로 답변들 조회")
    fun getAllByQuestionAnswerPairsByTestVersion() {
        // given
        val validQuestionAnswerPairs =
            listOf(1 to 2, 2 to 1, 3 to 2, 4 to 1, 5 to 1, 6 to 1, 7 to 1, 8 to 2, 9 to 1, 10 to 2, 11 to 1, 12 to 2)
        val invalidQuestionAnswerPairs =
            listOf(1 to 4, 2 to 1, 3 to 2, 4 to 1, 5 to 1, 6 to 1, 7 to 1, 8 to 2, 9 to 1, 10 to 2, 11 to 1, 12 to 2)

        // when
        val findAnswers =
            answerService.getAllByQuestionAnswerPairsByTestVersion(validQuestionAnswerPairs, 1)

        // then
        assertThat(findAnswers.size).isSameAs(12)
        assertThatThrownBy {
            answerService.getAllByQuestionAnswerPairsByTestVersion(invalidQuestionAnswerPairs, 1)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
