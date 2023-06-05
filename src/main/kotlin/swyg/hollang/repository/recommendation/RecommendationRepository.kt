package swyg.hollang.repository.recommendation

import swyg.hollang.entity.Recommendation

interface RecommendationRepository {

    fun save(recommendation: Recommendation): Recommendation

    fun findWithUserById(recommendationId: Long): Recommendation
}
