package swyg.hollang.repository.testresponse

import swyg.hollang.entity.TestResponse

interface TestResponseRepository {

    fun save(testResponse: TestResponse): TestResponse
    fun countAll(): Long
}
