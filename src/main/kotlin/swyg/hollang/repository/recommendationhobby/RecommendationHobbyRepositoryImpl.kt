package swyg.hollang.repository.recommendationhobby

import org.springframework.stereotype.Repository
import swyg.hollang.entity.RecommendationHobby

@Repository
class RecommendationHobbyRepositoryImpl(
    private val recommendationHobbyJpaRepository: RecommendationHobbyJpaRepository)
    : RecommendationHobbyRepository {

    override fun save(recommendationHobby: RecommendationHobby): RecommendationHobby {
        return recommendationHobbyJpaRepository.save(recommendationHobby)
    }

    override fun saveAll(recommendationHobbies: List<RecommendationHobby>): List<RecommendationHobby> {
        return recommendationHobbyJpaRepository.saveAll(recommendationHobbies)
    }

    override fun findAllByRecommendationId(recommendationId: Long) : List<RecommendationHobby> {
        return recommendationHobbyJpaRepository.findAllByRecommendationId(recommendationId)
    }
}
