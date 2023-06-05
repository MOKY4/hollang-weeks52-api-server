package swyg.hollang.repository.test

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Test

@Repository
class TestRepositoryImpl(private val testJpaRepository: TestJpaRepository) : TestRepository {

    override fun findByVersionWithQuestions(version: Long): Test {
        return testJpaRepository.findByVersionWithQuestions(version)
            ?: throw EntityNotFoundException("테스트가 존재하지 않습니다")
    }

}
