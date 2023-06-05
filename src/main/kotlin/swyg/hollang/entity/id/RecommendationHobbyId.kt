package swyg.hollang.entity.id

import swyg.hollang.entity.Hobby
import swyg.hollang.entity.Recommendation
import java.io.Serializable

data class RecommendationHobbyId(
    val recommendation: Recommendation? = null,
    val hobby: Hobby? = null
) : Serializable
