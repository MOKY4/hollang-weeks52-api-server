package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class TestResponse private constructor(

    @OneToOne(fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: User

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_response_id")
    val id: Long? = null

    @OneToMany(mappedBy = "testResponse", cascade = [ALL], orphanRemoval = true)
    var testResponseDetails: MutableList<TestResponseDetail> = mutableListOf()

    @OneToOne(mappedBy = "testResponse", cascade = [ALL], orphanRemoval = true)
    var recommendation: Recommendation? = null
        protected set(value) {
            field = value!!
            value.testResponse = this
            value.user = this.user
        }

    constructor(user: User, testResponseDetails: List<TestResponseDetail>, recommendation: Recommendation)
            : this(user) {
        this.testResponseDetails.addAll(testResponseDetails)
        testResponseDetails.forEach { testResponseDetail ->
            testResponseDetail.testResponse = this
        }
        this.recommendation = recommendation
    }
}
