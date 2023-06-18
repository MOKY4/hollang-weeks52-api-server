package swyg.hollang.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import swyg.hollang.entity.Hobby

data class GetHobbiesRankResponse(@JsonIgnore val hobbiesEntity: List<Hobby>) {
    val hobbies: List<HobbyDto> = hobbiesEntity.map { HobbyDto(it) }

    inner class HobbyDto(@JsonIgnore val hobbyEntity: Hobby){
        val id: Long = hobbyEntity.id!!
        val shortName: String = hobbyEntity.shortName
        val recommendCount: Long = hobbyEntity.recommendCount
        val imageUrl: String = hobbyEntity.imageUrl
    }
}
