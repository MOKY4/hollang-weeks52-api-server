package swyg.hollang.repository.hobbytype

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.HobbyType

interface HobbyTypeJpaRepository: JpaRepository<HobbyType, Long> {

    fun findByMbtiType(mbtiType: String): HobbyType?

    @Query("select ht from HobbyType ht " +
            "join fetch ht.fitHobbyTypes " +
            "where ht.mbtiType = :mbtiType")
    fun findByMbtiTypeWithFitHobbyTypes(mbtiType: String): HobbyType?

    fun findByMbtiTypeIsIn(mbtiTypes: List<String>): List<HobbyType>

    fun findByName(name: String): HobbyType?
}
