package swyg.hollang.entity

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Recommendation (

    @OneToOne
    @JoinColumn(name = "test_response_id", nullable = false, updatable = false)
    val testResponse: TestResponse,

    @ManyToOne
    @JoinColumn(name = "hobby_type_id", nullable = false, updatable = false)
    val hobbyType: HobbyType,

    @Type(JsonType::class)
    @Column(name = "mbti_score", columnDefinition = "json", nullable = false, updatable = false)
    val mbtiScore: List<Map<String, Int>> = listOf()

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    val id: Long? = null

    @OneToMany(
        mappedBy = "recommendation",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val recommendationHobbies: List<RecommendationHobby> = listOf()
}
