package swyg.hollang.repository.hobby

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import swyg.hollang.entity.Hobby

interface HobbyRepository {

    fun save(hobby: Hobby): Hobby

    fun findByNameIsIn(names: List<String>): List<Hobby>

    fun findAll(pageable: Pageable): Page<Hobby>

    fun incrementRecommendCountByNameIsIn(names: List<String>): Int

}
