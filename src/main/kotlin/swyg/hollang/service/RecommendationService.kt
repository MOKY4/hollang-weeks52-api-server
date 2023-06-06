package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Recommendation
import swyg.hollang.repository.recommendation.RecommendationRepository

@Service
@Transactional(readOnly = true)
class RecommendationService(private val recommendationRepository: RecommendationRepository) {

    fun save(recommendation: Recommendation): Recommendation {
        return recommendationRepository.save(recommendation)
    }

    fun getWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId: Long): Recommendation {
        return recommendationRepository.findByIdWithUserAndHobbyTypeAndHobbiesAndSurvey(recommendationId)
    }

    fun getWithUserAndHobbyTypeAndHobbiesById(recommendationId: Long) : Recommendation {
        return recommendationRepository.findByIdWithUserAndHobbyTypeAndHobbies(recommendationId)
    }

    fun getWithHobbiesById(recommendationId: Long) : Recommendation {
        return recommendationRepository.findByIdWithHobbies(recommendationId)
    }
}
