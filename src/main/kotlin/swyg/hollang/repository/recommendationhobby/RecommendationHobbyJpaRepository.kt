package swyg.hollang.repository.recommendationhobby

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.RecommendationHobby
import swyg.hollang.entity.id.RecommendationHobbyId

interface RecommendationHobbyJpaRepository : JpaRepository<RecommendationHobby, RecommendationHobbyId> {
}
