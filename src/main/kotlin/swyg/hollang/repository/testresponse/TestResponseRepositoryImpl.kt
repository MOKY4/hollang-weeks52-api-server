package swyg.hollang.repository.testresponse

import org.springframework.stereotype.Repository
import swyg.hollang.entity.TestResponse

@Repository
class TestResponseRepositoryImpl(private val testResponseJpaRepository: TestResponseJpaRepository)
    : TestResponseRepository {

    override fun save(testResponse: TestResponse): TestResponse {
        return testResponseJpaRepository.save(testResponse)
    }

    override fun countAll(): Long {
        return testResponseJpaRepository.countAll()
    }

}
