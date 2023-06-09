package swyg.hollang.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swyg.hollang.dto.GetTestDetailsResponse
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.service.test.TestService
import swyg.hollang.utils.WebProperties

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = ["\${client.server.host}"])
class TestController(private val testService: TestService) {

    @GetMapping
    fun getTestDetails(@RequestParam(value = "version", required = false, defaultValue = "1") version: Int)
        : ResponseEntity<SuccessResponse<GetTestDetailsResponse>> {
        val test = testService.getWithQuestionsAndAnswersByVersion(version)
        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                GetTestDetailsResponse(test)))
    }
}
