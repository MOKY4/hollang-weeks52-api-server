package swyg.hollang.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class MbtiScore(

    @Column(name = "score_e", nullable = false)
    val scoreE: Int,

    @Column(name = "score_n", nullable = false)
    val scoreN: Int,

    @Column(name = "score_f", nullable = false)
    val scoreF: Int,

    @Column(name = "score_j", nullable = false)
    val scoreJ: Int
)
