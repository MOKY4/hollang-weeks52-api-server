package swyg.hollang.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swyg.hollang.dto.CreateRecommendationSurveyRequest
import swyg.hollang.dto.RecommendHobbyAndTypesResponse
import swyg.hollang.dto.RecommendShareResponse
import swyg.hollang.dto.common.SuccessResponse
import swyg.hollang.manager.RecommendationManager
import swyg.hollang.utils.WebProperties

@RestController
@RequestMapping("/recommendations")
@CrossOrigin(origins = ["\${client.server.host}"])
class RecommendationController(private val recommendationManager: RecommendationManager) {

    @GetMapping("/{recommendationId}")
    fun recommendHobbyAndTypes(@PathVariable("recommendationId") recommendationId: Long)
        : ResponseEntity<SuccessResponse<RecommendHobbyAndTypesResponse>> {
        val recommendHobbyAndTypesResponse = recommendationManager.getUserRecommendation(recommendationId)
        return ResponseEntity.ok()
            .body(SuccessResponse(
                HttpStatus.OK.name,
                WebProperties.SUCCESS_RESPONSE_MESSAGE,
                recommendHobbyAndTypesResponse
            ))
    }

    @GetMapping("/{recommendationId}/shares")
    fun getRecommendationShare(@PathVariable("recommendationId") recommendationId: Long)
            : ResponseEntity<SuccessResponse<RecommendShareResponse>> {
        val recommendShareResponse = recommendationManager.getUserRecommendationWithoutFitHobbies(recommendationId)
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
