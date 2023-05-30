package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.GetTestDetailsDto
import swyg.hollang.repository.test.TestRepository

@Service
@Transactional(readOnly = true)
class TestService(private val testRepository: TestRepository) {

    fun findShuffledTestByVersion(version: Long): GetTestDetailsDto {
        val test = testRepository.findWithQuestionsAndAnswersByVersion(version)
        //질문 목록들을 섞는다
        test.questions.shuffle()
        return GetTestDetailsDto(testEntity = test)
    }
}
