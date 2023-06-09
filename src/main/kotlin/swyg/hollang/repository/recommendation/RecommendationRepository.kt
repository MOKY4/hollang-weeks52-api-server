package swyg.hollang.repository.recommendation

import swyg.hollang.entity.Recommendation

interface RecommendationRepository {

    fun findByIdWithUserAndHobbyTypeAndHobbiesAndSurvey(recommendationId: Long): Recommendation

    fun findByIdWithUserAndHobbyTypeAndHobbies(recommendationId: Long): Recommendation

    fun findByIdWithHobbies(recommendationId: Long): Recommendation
}
