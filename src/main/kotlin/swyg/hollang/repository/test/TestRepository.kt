package swyg.hollang.repository.test

import swyg.hollang.entity.Test

interface TestRepository {

    fun save(test: Test): Test

    fun findWithQuestionsAndAnswersByVersion(version: Int): Test

}
