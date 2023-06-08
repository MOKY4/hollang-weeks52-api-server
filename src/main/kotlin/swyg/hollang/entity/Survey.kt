package swyg.hollang.entity

import jakarta.persistence.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Survey private constructor(

    @Column(name = "satisfaction", nullable = false, updatable = false)
    val satisfaction: Int

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    val id: Long? = null

    @OneToOne
    @JoinColumns(
        JoinColumn(name = "hobby_id", nullable = false, updatable = false),
        JoinColumn(name = "recommendation_id", nullable = false, updatable = false)
    )
    var recommendationHobby: RecommendationHobby? = null
        set(value) {
            field = value!!
            value.survey = this
        }

    constructor(satisfaction: Int, recommendationHobby: RecommendationHobby): this(satisfaction) {
        this.recommendationHobby = recommendationHobby
    }
}
