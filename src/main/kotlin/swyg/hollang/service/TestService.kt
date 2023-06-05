package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.TestDetailsResponse
import swyg.hollang.repository.test.TestRepository

@Service
@Transactional(readOnly = true)
class TestService(private val testRepository: TestRepository) {

    fun findAndShuffleTestByVersion(version: Long): TestDetailsResponse {
        val test = testRepository.findByVersionWithQuestions(version)
        return TestDetailsResponse(testEntity = test)
    }
}
