package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.FitHobbyType
import swyg.hollang.entity.Hobby
import swyg.hollang.entity.HobbyType
import swyg.hollang.entity.Recommendation

data class GetRecommendationResponse(
    @JsonIgnore val recommendationEntity: Recommendation,
    @JsonIgnore val fitHobbyTypesEntity: List<HobbyType>,
    @JsonIgnore val fitHobbyTypeValues: List<FitHobbyType>){

    val recommendation: RecommendationDto = RecommendationDto()

    inner class RecommendationDto {
        val id: Long = recommendationEntity.id!!
        val didSurvey: Boolean = recommendationEntity.recommendationHobbies.any { it.survey != null }
        val user: UserDto = UserDto()
        val hobbyType: HobbyTypeDto = HobbyTypeDto(recommendationEntity.hobbyType)
        val hobbies: List<HobbyDto> = recommendationEntity.recommendationHobbies.map { HobbyDto(it.hobby, it.ranking) }
        val fitHobbyTypes: List<FitHobbyTypeDto> = fitHobbyTypesEntity.map { fitHobbyType ->
            val ranking = fitHobbyTypeValues.find {
                it.mbtiType == fitHobbyType.mbtiType
            }!!.ranking
            FitHobbyTypeDto(fitHobbyType, ranking)
        }
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
        val originalName: String = hobbyEntity.originalName
        val shortName: String = hobbyEntity.shortName
        val summary: String = hobbyEntity.summary
        val description: String = hobbyEntity.description
        val imageUrl: String = hobbyEntity.imageUrl
        val contentUrl: String = hobbyEntity.contentUrl
        val ranking: Int = hobbyRanking
    }

    inner class FitHobbyTypeDto(@JsonIgnore val hobbyTypeEntity: HobbyType, hobbyTypeRanking: Int) {
        val id: Long = hobbyTypeEntity.id!!
        val name: String = hobbyTypeEntity.name
        val description: String = hobbyTypeEntity.description
        val imageUrl: String = hobbyTypeEntity.imageUrl
        val ranking: Int = hobbyTypeRanking
    }
}



