package swyg.hollang.manager

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateRecommendationSurveyRequest
import swyg.hollang.dto.RecommendHobbyAndTypesResponse
import swyg.hollang.dto.RecommendShareResponse
import swyg.hollang.entity.Survey
import swyg.hollang.service.HobbyTypeService
import swyg.hollang.service.RecommendationHobbyService
import swyg.hollang.service.RecommendationService

@Component
class RecommendationManager(
    private val recommendationService: RecommendationService,
    private val hobbyTypeService: HobbyTypeService,
    private val recommendationHobbyService: RecommendationHobbyService
) {

    @Transactional(readOnly = true)
    fun getUserRecommendation(recommendationId: Long) : RecommendHobbyAndTypesResponse {
        //추천 id로 추천 결과를 가져온다.
        val findRecommendation = recommendationService
            .getRecommendationWithUserById(recommendationId = recommendationId)

        val hobbyType = findRecommendation.hobbyType

        //추천받은 취미 유형과 잘맞는 유형을 가져온다.
        val fitHobbyTypes = hobbyTypeService
            .getHobbyTypesByMbtiTypes(mbtiTypes = hobbyType.fitHobbyTypes)

        return RecommendHobbyAndTypesResponse(
            findRecommendation, findRecommendation.hobbyType, findRecommendation.recommendationHobbies, fitHobbyTypes
        )

    }

    @Transactional(readOnly = true)
    fun getUserRecommendationWithoutFitHobbies(recommendationId: Long) : RecommendShareResponse {
        val findRecommendation =
            recommendationService.getRecommendationWithoutSurveyById(recommendationId = recommendationId)

        return RecommendShareResponse(
            findRecommendation, findRecommendation.hobbyType, findRecommendation.recommendationHobbies)
    }

    @Transactional
    fun createRecommendationSurvey(recommendationId: Long, createRecommendationSurveyRequest: CreateRecommendationSurveyRequest) {
        createRecommendationSurveyRequest.survey.hobbies.forEach { hobby ->
            val recommendationHobby =
                recommendationHobbyService.getRecommendationHobbyById(recommendationId, hobby.id)
            recommendationHobby.survey = Survey(hobby.satisfaction)
        }
    }
}
