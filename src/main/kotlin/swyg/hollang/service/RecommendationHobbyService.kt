package swyg.hollang.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Hobby
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.RecommendationHobby
import swyg.hollang.repository.recommendationhobby.RecommendationHobbyRepository

@Service
@Transactional(readOnly = true)
class RecommendationHobbyService(private val recommendationHobbyRepository: RecommendationHobbyRepository) {

    fun createRecommendationHobbies(recommendationHobbies: List<RecommendationHobby>) : Int {
        return recommendationHobbyRepository.batchInsert(recommendationHobbies)
    }
}
