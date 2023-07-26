package swyg.hollang.service.answer

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.repository.answer.AnswerRepository
import java.lang.IllegalArgumentException

@Service
@Transactional(readOnly = true)
class AnswerService(private val answerRepository: AnswerRepository) {

    fun getAllByQuestionAnswerPairsByTestVersion(
        questionAnswerPairs: List<Pair<Int, Int>>,
        testVersion: Int
    ): List<Answer> {
        //테스트 응답 상세정보 엔티티 생성
        val findAnswers = answerRepository
            .findAllByQuestionAnswerPairsAndTestVersion(questionAnswerPairs, testVersion)

        return if(findAnswers.size != questionAnswerPairs.size) {
            throw IllegalArgumentException("존재하지 않은 답변 번호입니다")
        } else findAnswers
    }

}
