package swyg.hollang.repository.answer

import swyg.hollang.entity.Answer

interface AnswerRepository {

    fun findAllByQuestionAnswerPairsByTestVersion(questionAnswerPairs: List<Pair<Long, Long>>, testVersion: Long): List<Answer>
}
