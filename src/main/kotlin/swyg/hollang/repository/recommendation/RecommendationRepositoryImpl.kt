package swyg.hollang.repository.recommendation

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Recommendation

@Repository
class RecommendationRepositoryImpl(private val recommendationJpaRepository: RecommendationJpaRepository)
    : RecommendationRepository {

    override fun findWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId: Long): Recommendation {
        return recommendationJpaRepository.findWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId)
            ?: throw EntityNotFoundException("추천 $recommendationId 번을 찾을 수 없습니다")
    }

    override fun findWithUserAndHobbyTypeAndHobbiesById(recommendationId: Long): Recommendation {
        return recommendationJpaRepository.findWithUserAndHobbyTypeAndHobbiesById(recommendationId)
            ?: throw EntityNotFoundException("추천 $recommendationId 번을 찾을 수 없습니다")
    }

    override fun findWithHobbiesById(recommendationId: Long): Recommendation {
        return recommendationJpaRepository.findWithRecommendationHobbiesAndHobbiesById(recommendationId)
            ?: throw EntityNotFoundException("추천 $recommendationId 번을 찾을 수 없습니다")
    }
}
