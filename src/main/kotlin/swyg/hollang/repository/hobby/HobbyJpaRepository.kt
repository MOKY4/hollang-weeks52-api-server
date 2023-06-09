package swyg.hollang.repository.hobby

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Hobby

interface HobbyJpaRepository: JpaRepository<Hobby, Long> {

    fun findByNameIsIn(names: List<String>): List<Hobby>

    @Modifying
    @Query("update Hobby h set h.recommendCount = h.recommendCount + 1 where h.name in :names")
    fun incrementRecommendCountByNameIsIn(names: List<String>): Int
}
