package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User

data class CreateTestResponseResponse(@JsonIgnore val testResponseEntity: TestResponse) {
    val user: UserDto = UserDto(testResponseEntity.user!!)
    val recommendation: RecommendationDto = RecommendationDto(testResponseEntity.recommendation!!)

    inner class UserDto (@JsonIgnore val userEntity: User) {
        val id: Long = userEntity.id!!
        val name: String = userEntity.name
    }

    inner class RecommendationDto(@JsonIgnore val recommendationEntity: Recommendation) {
        val id: Long = recommendationEntity.id!!
    }
}

