package swyg.hollang.repository.test

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question

@ActiveProfiles(value = ["test"])
@SpringBootTest
@Transactional
internal class TestRepositoryTest(@Autowired val testRepository: TestRepository) {

    @Test
    fun save() {
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

        // when
        val savedTest = testRepository.save(test)

        // then
        assertThat(savedTest.version).isSameAs(1)
    }

    @Test
    fun findWithQuestionsAndAnswersByVersion() {
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
        testRepository.save(test)

        // when
        val findTest = testRepository.findWithQuestionsAndAnswersByVersion(1)

        // then
        assertThat(findTest.questions.size).isSameAs(12)
    }
}
