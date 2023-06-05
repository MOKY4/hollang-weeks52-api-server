package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Answer
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.TestResponseDetail
import swyg.hollang.repository.testresponsedetail.TestResponseDetailRepository

@Service
@Transactional(readOnly = true)
class TestResponseDetailService(
    private val testResponseDetailRepository: TestResponseDetailRepository) {

    fun createTestResponseDetails(testResponse: TestResponse, answers: List<Answer>) : Int {
        val testResponseDetails = answers.map {
            TestResponseDetail(testResponse = testResponse, answer = it)
        }

        return testResponseDetailRepository.batchInsert(testResponseDetails)
    }

}
