package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.repository.testresponsedetail.TestResponseDetailRepository

@Service
@Transactional(readOnly = true)
class TestResponseDetailService(
    private val testResponseDetailRepository: TestResponseDetailRepository) {

}
