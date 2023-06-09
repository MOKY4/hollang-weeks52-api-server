package swyg.hollang.repository.recommendation

import swyg.hollang.entity.Recommendation

interface RecommendationRepository {

    fun findWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId: Long): Recommendation

    fun findWithUserAndHobbyTypeAndHobbiesById(recommendationId: Long): Recommendation

    fun findWithHobbiesById(recommendationId: Long): Recommendation
}
