package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Survey(

    @OneToOne
    @JoinColumns(
        JoinColumn(name = "recommendation_id", nullable = false, updatable = false),
        JoinColumn(name = "hobby_id", nullable = false, updatable = false)
    )
    val recommendationHobby: RecommendationHobby,

    @Column(name = "satisfaction", nullable = false, updatable = false)
    val satisfaction: Int

) : BaseTimeEntity() {

    @Id
    @Column(name = "survey_id")
    val id: Long? = null
}
