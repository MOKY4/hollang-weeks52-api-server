package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Answer
import swyg.hollang.entity.Question
import swyg.hollang.entity.Test

/**
 * @JsonIgnore
 * 엔티티는 밖으로 노출(컨트롤러 단)되면 LAZY Loading init 예외 발생한다.
 * 그 이유는 트랜잭션 생명주기를 서비스와 dao단에서만 고정했기 떄문이다.
 */
data class GetTestDetailsResponse (@JsonIgnore val testEntity: Test) {
    val test: TestDto = TestDto()

    inner class TestDto {
        val id: Long = testEntity.id!!
        val version: Int = testEntity.version
        val questions: List<QuestionDto> = testEntity.questions.map { QuestionDto(it) }
    }

    inner class QuestionDto(@JsonIgnore val questionEntity: Question) {
        val id: Long = questionEntity.id!!
        val number: Int = questionEntity.number
        val content: String = questionEntity.content
        val answers: List<AnswerDto> = questionEntity.answers.map { AnswerDto(it) }
    }

    inner class AnswerDto(@JsonIgnore val answerEntity: Answer) {
        val id: Long = answerEntity.id!!
        val number: Int = answerEntity.number
        val content: String = answerEntity.content
    }
}






