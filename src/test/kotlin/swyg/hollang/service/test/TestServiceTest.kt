package swyg.hollang.service.test

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
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
internal class TestServiceTest(
    @Autowired val em: EntityManager,
    @Autowired val testService: TestService
) {

    @Test
    @DisplayName("테스트 버전에 해당하는 테스트 시트 조회")
    fun getWithQuestionsAndAnswersByVersion() {
        // given
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

        // when
        val findTest = testService.getWithQuestionsAndAnswersByVersion(1)

        // then
        assertThat(findTest.questions.size).isSameAs(12)
    }
}
