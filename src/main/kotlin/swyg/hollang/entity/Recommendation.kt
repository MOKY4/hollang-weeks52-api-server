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

    @Type(JsonType::class)
    @Column(name = "mbti_score", columnDefinition = "json", nullable = false, updatable = false)
    val mbtiScore: List<Map<String, Int>> = listOf()

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    val id: Long? = null

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "test_response_id", nullable = false, updatable = false)
    var testResponse: TestResponse? = null

    @OneToMany(
        mappedBy = "recommendation",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = LAZY
    )
    val recommendationHobbies: MutableList<RecommendationHobby> = mutableListOf()

    constructor(hobbyType: HobbyType, mbtiScore: List<Map<String, Int>>,
                recommendationHobbies: MutableList<RecommendationHobby>) : this(hobbyType, mbtiScore) {
        this.recommendationHobbies.addAll(recommendationHobbies)
        recommendationHobbies.forEach { recommendationHobby ->
            recommendationHobby.recommendation = recommendationHobby.recommendation ?: this
        }
    }
}
