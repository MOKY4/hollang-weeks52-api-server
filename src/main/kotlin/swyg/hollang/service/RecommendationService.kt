package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Recommendation
import swyg.hollang.repository.recommendation.RecommendationRepository

@Service
@Transactional(readOnly = true)
class RecommendationService(private val recommendationRepository: RecommendationRepository) {

    fun getRecommendationWithUserById(recommendationId: Long): Recommendation {
        return recommendationRepository.findByIdWithUserAndHobbyTypeAndHobbiesAndSurvey(recommendationId)
    }

    fun getRecommendationWithoutSurveyById(recommendationId: Long) : Recommendation {
        return recommendationRepository.findByIdWithUserAndHobbyTypeAndHobbies(recommendationId)
    }
}
