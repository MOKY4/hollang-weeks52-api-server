package swyg.hollang.dto

data class GetInferringResultResponse(
    val hobbyType: MutableMap<String, Any>,
    val hobbies: MutableList<MutableMap<String, Any>>
)
