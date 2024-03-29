package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Answer(

    @Column(name = "answer_number", nullable = false)
    val number: Int,

    @Column(name = "content", nullable = false)
    val content: String

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    val id: Long? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    var question: Question? = null
}
