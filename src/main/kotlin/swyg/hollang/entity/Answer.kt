package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Answer (

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    val question: Question,

    @Column(name = "answer_number", nullable = false)
    val number: Long,

    @Column(name = "content", nullable = false)
    val content: String

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    val id: Long? = null

}
