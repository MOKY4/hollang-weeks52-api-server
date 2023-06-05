package swyg.hollang.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Hobby
import swyg.hollang.repository.hobby.HobbyRepository

@Service
@Transactional(readOnly = true)
class HobbyService(private val hobbyRepository: HobbyRepository) {

    fun addHobbiesRecommendCount(hobbies: MutableList<MutableMap<String, String>>): List<Hobby> {
        val names = hobbies.map {
            val name = it["name"] ?: ""
            name.trim()
            name
        }
        val findHobbies = getHobbiesByName(names)
        if(findHobbies.size != 3){
            throw EntityNotFoundException("취미 ${names[0]}, ${names[1]}, ${names[2]} 중 하나를 찾을 수 없습니다.")
        }

        //추천받은 취미의 추천 카운트를 1씩 증가
        hobbyRepository.updateRecommendCountByName(names)

        return findHobbies
    }

    fun getHobbiesByName(names: List<String>): List<Hobby> {
        return hobbyRepository.findByNameIsIn(names)
    }

    fun getHobbies(pageable: Pageable): Page<Hobby> {
        return hobbyRepository.findAll(pageable)
    }
}
