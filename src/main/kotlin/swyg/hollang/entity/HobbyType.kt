package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
@Table(indexes = [Index(name = "idx_mbti_type", columnList = "mbti_type")])
class HobbyType(

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "img_url", nullable = false)
    val imageUrl: String,

    @Column(name = "mbti_type", nullable = false)
    val mbtiType: String,

    @ElementCollection(fetch = LAZY)
    @CollectionTable(
        name="fit_hobby_type",
        joinColumns = [JoinColumn(name = "hobby_type_id")]
    )
    val fitHobbyTypes: MutableSet<FitHobbyType> = mutableSetOf()

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_type_id")
    val id: Long? = null

}
