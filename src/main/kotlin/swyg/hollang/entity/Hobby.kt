package swyg.hollang.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
@DynamicInsert  //DML 작동시 null값이 아닌 값만 작동함
@Table(indexes = [Index(name = "idx_original_name", columnList = "original_name")])
class Hobby (

    @Column(name = "original_name", nullable = false)
    val originalName: String,

    @Column(name = "short_name", nullable = false)
    val shortName: String,

    @Column(name = "summary", nullable = false)
    val summary: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "img_url", nullable = false)
    val imageUrl: String,

    ) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    val id: Long? = null

    @Column(name = "recommend_count")
    @ColumnDefault(value = 0.toString())
    var recommendCount: Long = 0
}
