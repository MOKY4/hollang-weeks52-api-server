package swyg.hollang.repository.answer

import swyg.hollang.entity.Answer

interface AnswerRepository {

    fun save(answer: Answer): Answer

    fun findAllByQuestionAnswerPairsAndTestVersion(
        questionAnswerPairs: List<Pair<Int, Int>>, testVersion: Int): List<Answer>
}
