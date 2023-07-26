package swyg.hollang.service.hobby

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Hobby

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
internal class HobbyServiceTest(
    @Autowired val em: EntityManager,
    @Autowired val hobbyService: HobbyService
) {

    @Test
    @DisplayName("취미 이름들로 취미 추천 수 1씩 증가")
    fun incrementRecommendCountByNameIsIn() {
        // given
        val validNames = listOf("취미1", "취미2", "취미3")
        val invalidNames = listOf("취미4", "취미5", "취미6")
        val hobby1 = Hobby(validNames[0], "취미1 요약","취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby2 = Hobby(validNames[1], "취미2 요약","취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby3 = Hobby(validNames[2], "취미3 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)

        // when
        val incrementCount = hobbyService.incrementRecommendCountByOriginalNameIsIn(validNames)

        // then
        assertThat(incrementCount).isSameAs(3)
        assertThatThrownBy {
            hobbyService.incrementRecommendCountByOriginalNameIsIn(invalidNames)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("취미 이름들로 취미들 조회")
    fun getAllByNameIsIn() {
        // given
        val validNames = listOf("취미1", "취미2", "취미3")
        val invalidNames = listOf("취미4", "취미5", "취미6")
        val hobby1 = Hobby(validNames[0], "취미1 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby2 = Hobby(validNames[1], "취미2 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby3 = Hobby(validNames[2], "취미3 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)

        // when
        val findHobbies = hobbyService.getAllByOriginalNameIsIn(validNames)

        // then
        assertThat(findHobbies.size).isSameAs(3)
        assertThatThrownBy {
            hobbyService.getAllByOriginalNameIsIn(invalidNames)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("취미들 추천 수 순으로 모두 조회")
    fun getAll() {
        // given
        val names = listOf("취미1", "취미2", "취미3")
        val hobby1 = Hobby(names[0], "취미1 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        hobby1.recommendCount = 3
        val hobby2 = Hobby(names[1], "취미2 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        val hobby3 = Hobby(names[2], "취미3 요약", "취미 요약정보", "취미 상세정보", "https://example.com", "https://weeks52.me")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)

        // when
        val pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "recommendCount"))
        val findHobbies = hobbyService.getAll(pageRequest)

        // then
        assertThat(findHobbies.content.size).isSameAs(3)
        assertThat(findHobbies.content[0].recommendCount).isSameAs(3L)
    }
}
