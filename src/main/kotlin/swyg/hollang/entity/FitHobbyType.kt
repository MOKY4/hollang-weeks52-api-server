package swyg.hollang.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class FitHobbyType(
    @Column(name = "mbti_type", nullable = false, updatable = false)
    val mbtiType: String,
    @Column(name = "ranking", nullable = false, updatable = false)
    val ranking: Int,
)
