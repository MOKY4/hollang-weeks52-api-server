package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User
import swyg.hollang.repository.testresponse.TestResponseRepository

@Service
@Transactional(readOnly = true)
class TestResponseService(private val testResponseRepository: TestResponseRepository) {

    fun createTestResponse(createdUser: User): TestResponse {
        val testResponse = TestResponse(createdUser)
        return testResponseRepository.save(testResponse)
    }

    fun countAllTestResponse(): Long {
        return testResponseRepository.countAll()
    }
}
