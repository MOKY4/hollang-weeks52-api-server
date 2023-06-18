package swyg.hollang.service.hobby

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Hobby
import swyg.hollang.repository.hobby.HobbyRepository

@Service
@Transactional(readOnly = true)
class HobbyService(private val hobbyRepository: HobbyRepository) {

    fun incrementRecommendCountByOriginalNameIsIn(originalNames: List<String>): Int {
        //추천받은 취미의 추천 카운트를 1씩 증가
        val incrementCount = hobbyRepository.incrementRecommendCountByOriginalNameIsIn(originalNames)
        return if(incrementCount != originalNames.size) {
            throw IllegalArgumentException("찾을 수 없는 이름의 취미가 존재합니다")
        } else incrementCount
    }

    fun getAllByOriginalNameIsIn(originalNames: List<String>): List<Hobby> {
        val findHobbies = hobbyRepository.findByOriginalNameIsIn(originalNames)
        return if(findHobbies.size != originalNames.size) {
            throw IllegalArgumentException("찾을 수 없는 이름의 취미가 존재합니다")
        } else findHobbies
    }

    fun getAll(pageable: Pageable): Page<Hobby> {
        return hobbyRepository.findAll(pageable)
    }
}
