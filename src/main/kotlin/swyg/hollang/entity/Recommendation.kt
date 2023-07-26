package swyg.hollang.entity

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import org.hibernate.annotations.Type
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Recommendation private constructor(
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hobby_type_id", nullable = false, updatable = false)
    val hobbyType: HobbyType,

    @Embedded
    val mbtiScore: MbtiScore

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    val id: Long? = null

    @OneToOne(fetch = LAZY, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    var user: User? = null

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "test_response_id", nullable = false, updatable = false)
    var testResponse: TestResponse? = null

    @OneToMany(
        mappedBy = "recommendation",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = LAZY
    )
    val recommendationHobbies: MutableSet<RecommendationHobby> = mutableSetOf()

    constructor(hobbyType: HobbyType, mbtiScore: MbtiScore, recommendationHobbies: MutableList<RecommendationHobby>)
            : this(hobbyType, mbtiScore) {
        this.recommendationHobbies.addAll(recommendationHobbies)
        this.recommendationHobbies.forEach { recommendationHobby ->
            recommendationHobby.recommendation = this
        }
    }

}
