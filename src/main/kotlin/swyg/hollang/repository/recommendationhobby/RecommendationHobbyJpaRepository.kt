package swyg.hollang.repository.recommendationhobby

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.RecommendationHobby
import swyg.hollang.entity.id.RecommendationHobbyId

interface RecommendationHobbyJpaRepository : JpaRepository<RecommendationHobby, RecommendationHobbyId> {

    @Query("select distinct rh from RecommendationHobby rh " +
            "where rh.recommendation.id = :recommendationId")
    @EntityGraph(attributePaths = [
        "hobby",
        "survey",
        "recommendation"
    ])
    fun findAllByRecommendationId(recommendationId: Long) : List<RecommendationHobby>
}
