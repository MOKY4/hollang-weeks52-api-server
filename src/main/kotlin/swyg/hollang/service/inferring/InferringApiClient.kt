package swyg.hollang.service.inferring

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import swyg.hollang.config.FeignClientConfig
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.dto.GetInferringResultResponse

@FeignClient(
    name = "inferringApiClient",
    url = "\${inferring.server.host}:5000",
    configuration = [FeignClientConfig::class])
interface InferringApiClient {

    @PostMapping("/test-responses", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun inferHobbyTypeAndHobbies(@RequestBody createTestResponseDetailRequests: List<CreateTestResponseDetailRequest>)
        : GetInferringResultResponse
}
