package swyg.hollang.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class CreateTestResponseRequest(
    @field:Valid
    val user: CreateUserRequest,
    @field:Valid
    @field:NotEmpty(message = "테스트 응답 상세정보를 입력해주세요.")
    @field:Size(min = 12, max = 12, message = "12개의 질문에 모두 답해주세요.")
    val testResponseDetails: List<CreateTestResponseDetailRequest>)


data class CreateUserRequest(
    @field:Size(min = 1, max = 5, message = "사용자 이름은 최소 1글자 최대 5글자까지 가능합니다.")
    val name: String)

data class CreateTestResponseDetailRequest(
    @field:Min(value = 1, message = "질문 번호는 1번부터 시작합니다.")
    @field:Max(value = 12, message = "질문 번호는 12번까지 존재합니다.")
    val questionNumber: Int,
    @field:Min(value = 1, message = "답변 번호는 1번부터 시작합니다.")
    @field:Max(value = 2, message = "답변 번호는 2번까지 존재합니다.")
    val answerNumber: Int)
