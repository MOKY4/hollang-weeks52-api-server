package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import org.hibernate.annotations.BatchSize
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Question private constructor(

    @Column(name = "question_number", unique = true, nullable = false, updatable = false)
    val number: Long,

    @Column(name = "content", nullable = false)
    val content: String

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    val id: Long? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    var test: Test? = null

    @OneToMany(mappedBy = "question", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    @BatchSize(size = 100)
    var answers: MutableSet<Answer> = mutableSetOf()

    constructor(number: Long, content: String, answers: MutableSet<Answer>) : this(number, content) {
        this.answers.addAll(answers)
        answers.forEach { answer ->
            answer.question = answer.question ?: this
        }
    }
}
