package swyg.hollang.service.hobbytype

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.HobbyType
import swyg.hollang.repository.hobbytype.HobbyTypeRepository

@Service
@Transactional(readOnly = true)
class HobbyTypeService(private val hobbyTypeRepository: HobbyTypeRepository) {

    fun getByMbtiType(mbtiType: String): HobbyType {
        return hobbyTypeRepository.findByMbtiType(mbtiType)
    }

    fun getAllByMbtiTypeIsIn(mbtiTypes: List<String>): List<HobbyType> {
        val findHobbyTypes = hobbyTypeRepository.findByMbtiTypeIsIn(mbtiTypes)
        return if(findHobbyTypes.size != mbtiTypes.size) {
            throw IllegalArgumentException("찾을 수 없는 타입의 취미 유형이 존재합니다")
        } else findHobbyTypes
    }
}
