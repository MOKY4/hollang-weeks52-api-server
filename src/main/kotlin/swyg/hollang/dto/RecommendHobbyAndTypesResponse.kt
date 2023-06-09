package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.*

data class RecommendHobbyAndTypesResponse(
    @JsonIgnore val recommendationEntity: Recommendation,
    @JsonIgnore val hobbyTypeEntity: HobbyType,
    @JsonIgnore val hobbiesEntity: List<RecommendationHobby>,
    @JsonIgnore val fitHobbyTypesEntity: List<HobbyType>){

    val recommendation: RecommendationDto = RecommendationDto(
        recommendationEntity,
        hobbyTypeEntity,
        hobbiesEntity,
        fitHobbyTypesEntity
    )

    data class RecommendationDto(
        @JsonIgnore val recommendationEntity: Recommendation,
        @JsonIgnore val hobbyTypeEntity: HobbyType,
        @JsonIgnore val hobbiesEntity: List<RecommendationHobby>,
        @JsonIgnore val fitHobbyTypesEntity: List<HobbyType>
    ) {
        val id: Long = recommendationEntity.id!!
        val didSurvey: Boolean = hobbiesEntity[0].survey != null
        val user: UserDto = UserDto(recommendationEntity.testResponse!!.user!!)
        val hobbyType: HobbyTypeDto = HobbyTypeDto(hobbyTypeEntity)
        val hobbies: List<HobbyDto> = hobbiesEntity.map { HobbyDto(it.hobby) }
        val fitHobbyTypes: List<HobbyTypeDto> = fitHobbyTypesEntity.map { HobbyTypeDto(it) }
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



