package swyg.hollang.service.testresponse

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.TestResponse
import swyg.hollang.repository.testresponse.TestResponseRepository

@Service
@Transactional(readOnly = true)
class TestResponseService(private val testResponseRepository: TestResponseRepository) {

    fun create(testResponse: TestResponse): TestResponse {
        return testResponseRepository.save(testResponse)
    }

    fun countAll(): Long {
        return testResponseRepository.countAll()
    }
}
