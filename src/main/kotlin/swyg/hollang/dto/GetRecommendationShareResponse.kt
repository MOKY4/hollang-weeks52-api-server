package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Hobby
import swyg.hollang.entity.Recommendation

data class GetRecommendationShareResponse(@JsonIgnore val recommendationEntity: Recommendation){

    val recommendation: RecommendationDto = RecommendationDto()

    inner class RecommendationDto {
        val id: Long = recommendationEntity.id!!
        val user: UserDto = UserDto()
        val hobbyType: HobbyTypeDto = HobbyTypeDto()
        val hobbies: List<HobbyDto> = recommendationEntity.recommendationHobbies.map { HobbyDto(it.hobby, it.ranking) }
    }

    inner class UserDto {
        val id: Long = recommendationEntity.user!!.id!!
        val name: String = recommendationEntity.user!!.name
    }

    inner class HobbyTypeDto {
        val id: Long = recommendationEntity.hobbyType.id!!
        val name: String = recommendationEntity.hobbyType.name
        val description: String = recommendationEntity.hobbyType.description
        val imageUrl: String = recommendationEntity.hobbyType.imageUrl
    }

    inner class HobbyDto(@JsonIgnore val hobbyEntity: Hobby, hobbyRanking: Int) {
        val id: Long = hobbyEntity.id!!
        val originalName: String = hobbyEntity.originalName
        val shortName: String = hobbyEntity.shortName
        val summary: String = hobbyEntity.summary
        val description: String = hobbyEntity.description
        val imageUrl: String = hobbyEntity.imageUrl
        val ranking: Int = hobbyRanking
    }
}



