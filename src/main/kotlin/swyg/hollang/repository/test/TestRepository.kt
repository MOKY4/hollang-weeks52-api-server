package swyg.hollang.repository.test

import swyg.hollang.entity.Test

interface TestRepository {

    fun findByVersionWithQuestions(version: Long): Test

}
