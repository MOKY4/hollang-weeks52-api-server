package swyg.hollang.dto

data class CreateRecommendationResultResponse(
    val hobbyType: MutableMap<String, Any>,
    val hobbies: MutableList<MutableMap<String, String>>
)
