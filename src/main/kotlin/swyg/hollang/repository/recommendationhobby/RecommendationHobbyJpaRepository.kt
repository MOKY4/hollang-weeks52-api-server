package swyg.hollang.repository.recommendationhobby

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.RecommendationHobby
import swyg.hollang.entity.id.RecommendationHobbyId

interface RecommendationHobbyJpaRepository : JpaRepository<RecommendationHobby, RecommendationHobbyId> {

    @Query("select rh from RecommendationHobby rh " +
            "where rh.recommendation.id = :recommendationId and rh.hobby.id = :hobbyId")
    fun findByRecommendationIdAndHobbyId(recommendationId: Long, hobbyId: Long) : RecommendationHobby?
}
