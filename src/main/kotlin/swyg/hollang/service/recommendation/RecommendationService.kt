package swyg.hollang.service.recommendation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Recommendation
import swyg.hollang.repository.recommendation.RecommendationRepository

@Service
@Transactional(readOnly = true)
class RecommendationService(private val recommendationRepository: RecommendationRepository) {

    fun getWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId: Long): Recommendation {
        return recommendationRepository.findWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId)
    }

    fun getWithUserAndHobbyTypeAndHobbiesById(recommendationId: Long) : Recommendation {
        return recommendationRepository.findWithUserAndHobbyTypeAndHobbiesById(recommendationId)
    }

    fun getWithHobbiesById(recommendationId: Long) : Recommendation {
        return recommendationRepository.findWithHobbiesById(recommendationId)
    }
}
