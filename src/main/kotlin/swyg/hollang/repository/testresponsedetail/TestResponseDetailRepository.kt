package swyg.hollang.repository.testresponsedetail

import swyg.hollang.entity.TestResponseDetail

interface TestResponseDetailRepository {

    fun save(testResponseDetail: TestResponseDetail): TestResponseDetail

    fun batchInsert(testResponseDetails: List<TestResponseDetail>) : Int
}
