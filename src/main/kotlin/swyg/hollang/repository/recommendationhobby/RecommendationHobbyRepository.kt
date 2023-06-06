package swyg.hollang.repository.recommendationhobby

import swyg.hollang.entity.RecommendationHobby

interface RecommendationHobbyRepository {

    fun save(recommendationHobby: RecommendationHobby): RecommendationHobby

    fun saveAll(recommendationHobbies: List<RecommendationHobby>): List<RecommendationHobby>

    fun findAllByRecommendationId(recommendationId: Long) : List<RecommendationHobby>
}
