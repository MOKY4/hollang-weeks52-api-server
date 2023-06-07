package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.TestResponse

data class CreateTestResponseResponse(@JsonIgnore val testResponseEntity: TestResponse) {
    val user: UserDto = UserDto()
    val recommendation: RecommendationDto = RecommendationDto()

    inner class UserDto {
        val id: Long = testResponseEntity.user.id!!
        val name: String = testResponseEntity.user.name
    }

    inner class RecommendationDto {
        val id: Long = testResponseEntity.recommendation!!.id!!
    }
}

