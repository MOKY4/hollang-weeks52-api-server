package swyg.hollang.repository.testresponsedetail

import org.springframework.stereotype.Repository
import swyg.hollang.entity.TestResponseDetail

@Repository
class TestResponseDetailRepositoryImpl(
    private val testResponseDetailJpaRepository: TestResponseDetailJpaRepository)
    : TestResponseDetailRepository {

    override fun save(testResponseDetail: TestResponseDetail): TestResponseDetail {
        return testResponseDetailJpaRepository.save(testResponseDetail)
    }

    override fun saveAll(testResponseDetails: List<TestResponseDetail>): List<TestResponseDetail> {
        return testResponseDetailJpaRepository.saveAll(testResponseDetails)
    }
}
