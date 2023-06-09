package swyg.hollang.service.test

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Test
import swyg.hollang.repository.test.TestRepository

@Service
@Transactional(readOnly = true)
class TestService(private val testRepository: TestRepository) {

    fun getWithQuestionsAndAnswersByVersion(version: Int): Test {
        return testRepository.findWithQuestionsAndAnswersByVersion(version)
    }
}
