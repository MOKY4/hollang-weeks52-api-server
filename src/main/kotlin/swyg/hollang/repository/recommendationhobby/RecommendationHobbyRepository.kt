package swyg.hollang.repository.recommendationhobby

import swyg.hollang.entity.RecommendationHobby

interface RecommendationHobbyRepository {

    fun save(recommendationHobby: RecommendationHobby): RecommendationHobby

    fun batchInsert(recommendationHobbies: List<RecommendationHobby>) : Int

    fun findByRecommendationIdAndHobbyId(recommendationId: Long, hobbyId: Long) : RecommendationHobby
}
