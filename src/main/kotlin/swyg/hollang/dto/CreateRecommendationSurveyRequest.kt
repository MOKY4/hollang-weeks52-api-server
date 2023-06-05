package swyg.hollang.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateRecommendationSurveyRequest(@field:Valid val survey: CreateSurveyRequest)

data class CreateSurveyRequest(@field:Valid val hobbies: List<HobbyInfo>)

data class HobbyInfo(
    @field:NotNull(message = "취미 id를 입력해주세요")
    val id: Long,

    @field:NotEmpty(message = "취미 이름을 입력해주세요")
    val name: String,

    @field:NotNull(message = "취미 만족도를 입력해주세요")
    val satisfaction: Int)
