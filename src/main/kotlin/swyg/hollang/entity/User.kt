package swyg.hollang.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import swyg.hollang.entity.common.BaseTimeEntity

@Entity
class User (

    @Column(name = "name", nullable = false, length = 3)
    val name: String,

) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null

    @OneToOne(mappedBy = "user", cascade = [ALL], orphanRemoval = true)
    var testResponse: TestResponse? = null

}
