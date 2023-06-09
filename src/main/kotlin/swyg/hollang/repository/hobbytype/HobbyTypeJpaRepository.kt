package swyg.hollang.repository.hobbytype

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.HobbyType

interface HobbyTypeJpaRepository: JpaRepository<HobbyType, Long> {

    fun findByMbtiType(mbtiType: String): HobbyType?

    fun findByMbtiTypeIsIn(mbtiTypes: List<String>): List<HobbyType>

}
