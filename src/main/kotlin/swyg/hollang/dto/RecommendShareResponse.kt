package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.*

data class RecommendShareResponse(@JsonIgnore val recommendationEntity: Recommendation){

    val recommendation: RecommendationDto = RecommendationDto(recommendationEntity)

    data class RecommendationDto(@JsonIgnore val recommendationEntity: Recommendation) {
        val id: Long = recommendationEntity.id!!
        val user: UserDto = UserDto(recommendationEntity.user!!)
        val hobbyType: HobbyTypeDto = HobbyTypeDto(recommendationEntity.hobbyType)
        val hobbies: List<HobbyDto> = recommendationEntity.recommendationHobbies.map { HobbyDto(it.hobby) }
    }

    data class UserDto(@JsonIgnore val userEntity: User){
        val id: Long = userEntity.id!!
        val name: String = userEntity.name
    }

    data class HobbyTypeDto(@JsonIgnore val hobbyTypeEntity: HobbyType) {
        val id: Long = hobbyTypeEntity.id!!
        val name: String = hobbyTypeEntity.name
        val description: String = hobbyTypeEntity.description
        val imageUrl: String = hobbyTypeEntity.imageUrl
    }

    data class HobbyDto(@JsonIgnore val hobbyEntity: Hobby) {
        val id: Long = hobbyEntity.id!!
        val name: String = hobbyEntity.name
        val summary: String = hobbyEntity.summary
        val description: String = hobbyEntity.description
        val imageUrl: String = hobbyEntity.imageUrl
    }
}



