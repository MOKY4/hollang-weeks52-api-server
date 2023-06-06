package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class TestResponse() : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_response_id")
    val id: Long? = null

    @OneToOne(fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    var user: User? = null
        private set(value) {
            field = value!!
            value.testResponse = value.testResponse ?: this
        }

    @OneToOne(mappedBy = "testResponse", cascade = [ALL], orphanRemoval = true)
    var recommendation: Recommendation? = null
        private set(value) {
            field = value!!
            value.testResponse = value.testResponse ?: this
        }

    @OneToMany(mappedBy = "testResponse", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    val testResponseDetails: MutableList<TestResponseDetail> = mutableListOf()

    constructor(user: User, recommendation: Recommendation, testResponseDetails: List<TestResponseDetail>) : this() {
        this.user = user
        this.recommendation = recommendation
        this.testResponseDetails.addAll(testResponseDetails)
        testResponseDetails.forEach{ testResponseDetail ->
            testResponseDetail.testResponse = testResponseDetail.testResponse ?: this
        }
    }
}
