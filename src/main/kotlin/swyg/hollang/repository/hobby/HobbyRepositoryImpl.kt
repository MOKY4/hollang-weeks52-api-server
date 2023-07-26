package swyg.hollang.repository.hobby

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import swyg.hollang.entity.Hobby

@Repository
class HobbyRepositoryImpl(private val hobbyJpaRepository: HobbyJpaRepository): HobbyRepository {

    override fun save(hobby: Hobby): Hobby {
        return hobbyJpaRepository.save(hobby)
    }

    override fun findByOriginalNameIsIn(names: List<String>): List<Hobby> {
        return hobbyJpaRepository.findByOriginalNameIsIn(names)
    }

    override fun findAll(pageable: Pageable): Page<Hobby> {
        return hobbyJpaRepository.findAll(pageable)
    }

    override fun incrementRecommendCountByOriginalNameIsIn(names: List<String>): Int {
        return hobbyJpaRepository.incrementRecommendCountByNameIsIn(names)
    }
}
