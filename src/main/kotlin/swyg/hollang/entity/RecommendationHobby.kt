package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity
import swyg.hollang.entity.id.RecommendationHobbyId

@Entity
@IdClass(RecommendationHobbyId::class)
class RecommendationHobby(

    @Id
    @ManyToOne
    @JoinColumn(name = "recommendation_id", nullable = false, updatable = false)
    val recommendation: Recommendation,

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hobby_id", nullable = false, updatable = false)
    val hobby: Hobby,

) : BaseTimeEntity() {

    @OneToOne(mappedBy = "recommendationHobby", cascade = [CascadeType.ALL], orphanRemoval = true)
    val survey: Survey? = null
}
