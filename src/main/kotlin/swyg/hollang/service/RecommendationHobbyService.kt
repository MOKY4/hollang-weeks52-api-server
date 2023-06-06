package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.RecommendationHobby
import swyg.hollang.repository.recommendationhobby.RecommendationHobbyRepository

@Service
@Transactional(readOnly = true)
class RecommendationHobbyService(private val recommendationHobbyRepository: RecommendationHobbyRepository) {

    fun getRecommendationHobbyById(recommendationId: Long) : List<RecommendationHobby> {
        return recommendationHobbyRepository.findAllByRecommendationId(recommendationId)
    }
}
