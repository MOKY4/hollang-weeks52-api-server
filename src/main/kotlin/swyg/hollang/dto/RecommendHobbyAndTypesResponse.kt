package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Hobby
import swyg.hollang.entity.HobbyType
import swyg.hollang.entity.Recommendation

data class RecommendHobbyAndTypesResponse(
    @JsonIgnore val recommendationEntity: Recommendation,
    @JsonIgnore val fitHobbyTypesEntity: List<HobbyType>){

    val recommendation: RecommendationDto = RecommendationDto()

    inner class RecommendationDto {
        val id: Long = recommendationEntity.id!!
        val didSurvey: Boolean = recommendationEntity.recommendationHobbies.any { it.survey != null }
        val user: UserDto = UserDto()
        val hobbyType: HobbyTypeDto = HobbyTypeDto(recommendationEntity.hobbyType)
        val hobbies: List<HobbyDto> = recommendationEntity.recommendationHobbies.map { HobbyDto(it.hobby, it.ranking) }
        val fitHobbyTypes: List<HobbyTypeDto> = fitHobbyTypesEntity.map { HobbyTypeDto(it) }
    }

    inner class UserDto {
        val id: Long = recommendationEntity.user!!.id!!
        val name: String = recommendationEntity.user!!.name
    }

    inner class HobbyTypeDto(@JsonIgnore val hobbyTypeEntity: HobbyType) {
        val id: Long = hobbyTypeEntity.id!!
        val name: String = hobbyTypeEntity.name
        val description: String = hobbyTypeEntity.description
        val imageUrl: String = hobbyTypeEntity.imageUrl
    }

    inner class HobbyDto(@JsonIgnore val hobbyEntity: Hobby, hobbyRanking: Int) {
        val id: Long = hobbyEntity.id!!
        val name: String = hobbyEntity.name
        val summary: String = hobbyEntity.summary
        val description: String = hobbyEntity.description
        val imageUrl: String = hobbyEntity.imageUrl
        val ranking: Int = hobbyRanking
    }
}



