package swyg.hollang.manager

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateRecommendationSurveyRequest
import swyg.hollang.dto.RecommendHobbyAndTypesResponse
import swyg.hollang.dto.RecommendShareResponse
import swyg.hollang.entity.Survey
import swyg.hollang.service.HobbyTypeService
import swyg.hollang.service.RecommendationService

@Component
class RecommendationManager(
    private val recommendationService: RecommendationService,
    private val hobbyTypeService: HobbyTypeService
) {

    @Transactional(readOnly = true)
    fun getUserRecommendation(recommendationId: Long) : RecommendHobbyAndTypesResponse {
        //추천 id로 추천 결과를 가져온다.
        val findRecommendation = recommendationService
            .getWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId = recommendationId)

        val hobbyType = findRecommendation.hobbyType

        //추천받은 취미 유형과 잘맞는 유형을 가져온다.
        val fitHobbyTypes = hobbyTypeService
            .getHobbyTypesByMbtiTypes(mbtiTypes = hobbyType.fitHobbyTypes)

        return RecommendHobbyAndTypesResponse(
            findRecommendation, fitHobbyTypes
        )

    }

    @Transactional(readOnly = true)
    fun getUserRecommendationWithoutFitHobbies(recommendationId: Long) : RecommendShareResponse {
        val findRecommendation =
            recommendationService.getWithUserAndHobbyTypeAndHobbiesById(recommendationId = recommendationId)

        return RecommendShareResponse(findRecommendation)
    }

    @Transactional
    fun createRecommendationSurvey(recommendationId: Long, createRecommendationSurveyRequest: CreateRecommendationSurveyRequest) {
        val recommendation = recommendationService.getWithHobbiesById(recommendationId)

        createRecommendationSurveyRequest.survey.hobbies.forEach { hobby ->
            val recommendationHobby = recommendation.recommendationHobbies.find { it.hobby.id == hobby.id }
                ?: throw EntityNotFoundException("취미 ${hobby.id}번이 존재하지 않습니다")

            recommendationHobby.survey = Survey(hobby.satisfaction, recommendationHobby)
        }
    }
}
