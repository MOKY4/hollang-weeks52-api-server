package swyg.hollang.repository.test

import swyg.hollang.entity.Test

interface TestRepository {

    fun save(test: Test): Test

    fun findByVersionWithQuestions(version: Int): Test

}
