package swyg.hollang.repository.recommendation

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Recommendation

@Repository
class RecommendationRepositoryImpl(private val recommendationJpaRepository: RecommendationJpaRepository)
    : RecommendationRepository {

    override fun save(recommendation: Recommendation): Recommendation {
        return recommendationJpaRepository.save(recommendation)
    }

    override fun findByIdWithUserAndHobbyTypeAndHobbiesAndSurvey(recommendationId: Long): Recommendation {
        return recommendationJpaRepository.findByIdWithUserAndHobbyTypeAndHobbiesAndSurvey(recommendationId)
            ?: throw EntityNotFoundException("추천 $recommendationId 번을 찾을 수 없습니다")
    }

    override fun findByIdWithUserAndHobbyTypeAndHobbies(recommendationId: Long): Recommendation {
        return recommendationJpaRepository.findByIdWithUserAndHobbyTypeAndHobbies(recommendationId)
            ?: throw EntityNotFoundException("추천 $recommendationId 번을 찾을 수 없습니다")
    }

    override fun findByIdWithHobbies(recommendationId: Long): Recommendation {
        return recommendationJpaRepository.findByIdWithRecommendationHobbiesAndHobbies(recommendationId)
            ?: throw EntityNotFoundException("추천 $recommendationId 번을 찾을 수 없습니다")
    }
}
