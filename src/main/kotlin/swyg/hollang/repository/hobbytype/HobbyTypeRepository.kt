package swyg.hollang.repository.hobbytype

import swyg.hollang.entity.HobbyType

interface HobbyTypeRepository {

    fun findHobbyTypeByMbtiType(mbtiType: String): HobbyType

    fun findWithFitHobbyTypesByMbtiType(mbtiType: String): HobbyType

    fun findByMbtiTypeIsIn(mbtiTypes: List<String>): List<HobbyType>

    fun findByName(name: String): HobbyType
}
