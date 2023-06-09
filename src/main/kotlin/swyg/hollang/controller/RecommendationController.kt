package swyg.hollang.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swyg.hollang.dto.CreateRecommendationSurveyRequest
import swyg.hollang.dto.GetRecommendationResponse
import swyg.hollang.dto.GetRecommendationShareResponse
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.manager.RecommendationManager
import swyg.hollang.utils.WebProperties

@RestController
@RequestMapping("/recommendations")
@CrossOrigin(origins = ["\${client.server.host}"])
class RecommendationController(private val recommendationManager: RecommendationManager) {

    @GetMapping("/{recommendationId}")
    fun getRecommendationDetails(@PathVariable("recommendationId") recommendationId: Long)
        : ResponseEntity<SuccessResponse<GetRecommendationResponse>> {
        val recommendHobbyAndTypesResponse = recommendationManager.getRecommendationById(recommendationId)
        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                recommendHobbyAndTypesResponse
            ))
    }

    @GetMapping("/{recommendationId}/shares")
    fun getRecommendationShareDetails(@PathVariable("recommendationId") recommendationId: Long)
            : ResponseEntity<SuccessResponse<GetRecommendationShareResponse>> {
        val recommendShareResponse = recommendationManager.getRecommendationWithoutFitHobbiesById(recommendationId)
        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                recommendShareResponse
            ))
    }

    @PostMapping("/{recommendationId}/surveys")
    fun addRecommendationSurveys(@PathVariable("recommendationId") recommendationId: Long,
                                 @Valid @RequestBody createRecommendationSurveyRequest: CreateRecommendationSurveyRequest
    )
            : ResponseEntity<SuccessResponse<Unit?>> {
        recommendationManager.createRecommendationSurvey(recommendationId, createRecommendationSurveyRequest)

        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                null
            ))
    }
}
