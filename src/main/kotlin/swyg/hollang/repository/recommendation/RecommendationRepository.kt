package swyg.hollang.repository.recommendation

import swyg.hollang.entity.Recommendation

interface RecommendationRepository {

    fun save(recommendation: Recommendation): Recommendation

    fun findByIdWithUserAndHobbyTypeAndHobbiesAndSurvey(recommendationId: Long): Recommendation

    fun findByIdWithUserAndHobbyTypeAndHobbies(recommendationId: Long): Recommendation
}
