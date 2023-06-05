package swyg.hollang.repository.hobbytype

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import swyg.hollang.entity.HobbyType

@Repository
class HobbyTypeRepositoryImpl(private val hobbyTypeJpaRepository: HobbyTypeJpaRepository)
    : HobbyTypeRepository {

    override fun findHobbyTypeByMbtiType(mbtiType: String): HobbyType {
        return hobbyTypeJpaRepository.findHobbyTypeByMbtiType(mbtiType)
            ?: throw EntityNotFoundException("홀랑 유형 $mbtiType 을 찾을 수 없습니다.")
    }

    override fun findWithFitHobbyTypesByMbtiType(mbtiType: String): HobbyType {
        return hobbyTypeJpaRepository.findWithFitHobbyTypesByMbtiType(mbtiType)
            ?: throw EntityNotFoundException("홀랑 유형 $mbtiType 을 찾을 수 없습니다.")
    }

    override fun findByMbtiTypeIsIn(mbtiTypes: List<String>): List<HobbyType> {
        return hobbyTypeJpaRepository.findByMbtiTypeIsIn(mbtiTypes)
    }

    override fun findByName(name: String): HobbyType {
        return hobbyTypeJpaRepository.findByName(name)
            ?: throw EntityNotFoundException("홀랑 유형 $name 을 찾을 수 없습니다")
    }

}
