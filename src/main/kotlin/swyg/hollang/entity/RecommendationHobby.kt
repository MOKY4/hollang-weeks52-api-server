package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity
import swyg.hollang.entity.id.RecommendationHobbyId

@Entity
@IdClass(RecommendationHobbyId::class)
class RecommendationHobby(

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hobby_id", nullable = false, updatable = false)
    val hobby: Hobby,

    @Column(name = "ranking", nullable = false, updatable = false)
    val ranking: Int

) : BaseTimeEntity() {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recommendation_id", nullable = false, updatable = false)
    var recommendation: Recommendation? = null

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "survey_id", nullable = true, updatable = true)
    var survey: Survey? = null
}
