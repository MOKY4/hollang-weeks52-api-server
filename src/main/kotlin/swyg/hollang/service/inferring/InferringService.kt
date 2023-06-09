package swyg.hollang.service.inferring

import org.springframework.stereotype.Service
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.dto.GetInferringResultResponse

@Service
class InferringService(private val inferringApiClient: InferringApiClient) {

    fun inferByQuestionAnswerPairs(createTestResponseDetailRequests: List<CreateTestResponseDetailRequest>)
        : GetInferringResultResponse {
        return inferringApiClient.inferHobbyTypeAndHobbies(createTestResponseDetailRequests)
    }
}
