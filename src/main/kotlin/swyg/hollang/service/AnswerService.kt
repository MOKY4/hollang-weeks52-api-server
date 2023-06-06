package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.repository.answer.AnswerRepository

@Service
@Transactional(readOnly = true)
class AnswerService(private val answerRepository: AnswerRepository) {

    fun getAnswersByQuestionAnswerPairsByTestVersion(
        questionAnswerPairs: List<Pair<Long, Long>>,
        testVersion: Long
    ): List<Answer> {
        //테스트 응답 상세정보 엔티티 생성
        return answerRepository
            .findAllByQuestionAnswerPairsByTestVersion(questionAnswerPairs, testVersion)
    }

}
