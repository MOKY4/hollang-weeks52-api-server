package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.HobbyType
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.TestResponse
import swyg.hollang.repository.recommendation.RecommendationRepository

@Service
@Transactional(readOnly = true)
class RecommendationService(private val recommendationRepository: RecommendationRepository) {

    @Transactional
    fun save(testResponse: TestResponse, hobbyType: HobbyType, mbtiScore: List<Map<String, Int>>)
        : Recommendation {
        val recommendation = Recommendation(testResponse, hobbyType, mbtiScore)
        return recommendationRepository.save(recommendation)
    }

    fun getRecommendationWithUserById(recommendationId: Long): Recommendation {
        return recommendationRepository.findWithUserById(recommendationId)
    }
}
