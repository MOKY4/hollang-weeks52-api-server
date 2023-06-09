package swyg.hollang.repository.test

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Test

@Repository
class TestRepositoryImpl(private val testJpaRepository: TestJpaRepository) : TestRepository {

    override fun save(test: Test): Test {
        return testJpaRepository.save(test)
    }

    override fun findWithQuestionsAndAnswersByVersion(version: Int): Test {
        return testJpaRepository.findWithQuestionsAndAnswersByVersion(version)
            ?: throw EntityNotFoundException("테스트가 존재하지 않습니다")
    }

}
