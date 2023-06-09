package swyg.hollang.repository.hobby

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Hobby


@ActiveProfiles(value = ["test"])
@SpringBootTest
@Transactional
internal class HobbyRepositoryTest(@Autowired val hobbyRepository: HobbyRepository) {

    @Test
    @DisplayName("취미 이름들로 취미들 조회")
    fun findByNameIsIn() {
        // given
        val hobby1 = Hobby("취미1", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby2 = Hobby("취미2", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby3 = Hobby("취미3", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobbyRepository.save(hobby1)
        hobbyRepository.save(hobby2)
        hobbyRepository.save(hobby3)

        // when
        val hobbyNames = listOf("취미1", "취미2", "취미3")
        val findHobbies = hobbyRepository.findByNameIsIn(hobbyNames)

        // then
        assertThat(findHobbies.size).isSameAs(3)
    }

    @Test
    @DisplayName("모든 취미들을 정렬하여 조회")
    fun findAll() {
        // given
        val hobby1 = Hobby("취미1", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobby1.recommendCount = 3
        val hobby2 = Hobby("취미2", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobby2.recommendCount = 2
        val hobby3 = Hobby("취미3", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobby3.recommendCount = 1
        hobbyRepository.save(hobby1)
        hobbyRepository.save(hobby2)
        hobbyRepository.save(hobby3)

        // when
        val pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "recommendCount"))
        val findHobbies = hobbyRepository.findAll(pageRequest)

        // then
        assertThat(findHobbies.content.size).isSameAs(3)
        assertThat(findHobbies.content[0].recommendCount > findHobbies.content[1].recommendCount).isTrue
    }

    @Test
    @DisplayName("취미 이름들로 취미들의 추천수를 1씩 증가")
    fun updateRecommendCountByNames() {
        // given
        val hobby1 = Hobby("취미1", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobby1.recommendCount = 3
        val hobby2 = Hobby("취미2", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobby2.recommendCount = 2
        val hobby3 = Hobby("취미3", "취미 요약정보", "취미 상세정보", "https://example.com")
        hobby3.recommendCount = 1
        hobbyRepository.save(hobby1)
        hobbyRepository.save(hobby2)
        hobbyRepository.save(hobby3)

        // when
        val hobbyNames = listOf("취미1", "취미2", "취미3")
        val updatedHobbyCount = hobbyRepository.incrementRecommendCountByNames(names = hobbyNames)

        // then
        assertThat(updatedHobbyCount).isSameAs(3)
    }
}
