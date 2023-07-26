package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class Test private constructor(

    @Column(name = "version", unique = true, updatable = false, nullable = false)
    val version: Int

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    val id: Long? = null

    @OneToMany(mappedBy = "test", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    val questions: MutableSet<Question> = mutableSetOf()

    constructor(version: Int, questions: MutableSet<Question>) : this(version) {
        this.questions.addAll(questions)
        questions.forEach { question ->
            question.test = question.test ?: this
        }
    }
}
