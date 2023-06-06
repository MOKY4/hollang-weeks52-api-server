package swyg.hollang.repository.testresponsedetail

import swyg.hollang.entity.TestResponseDetail

interface TestResponseDetailRepository {

    fun save(testResponseDetail: TestResponseDetail): TestResponseDetail

    fun saveAll(testResponseDetails: List<TestResponseDetail>): List<TestResponseDetail>
}
