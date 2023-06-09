package swyg.hollang.repository.hobbytype

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.FitHobbyType
import swyg.hollang.entity.HobbyType

@ActiveProfiles(value = ["test"])
@SpringBootTest
@Transactional
internal class HobbyTypeRepositoryTest(@Autowired val hobbyTypeRepository: HobbyTypeRepository) {

    @Test
    @DisplayName("MBTI 타입으로 취미 유형 조회")
    fun findByMbtiType() {
        // given
        val fitHobbyTypes = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType = HobbyType(
            "취미 유형1", "취미 유형 상세정보", "https://example.com", "INTJ", fitHobbyTypes
        )
        hobbyTypeRepository.save(hobbyType)

        // when
        val findHobbyType = hobbyTypeRepository.findByMbtiType("INTJ")

        // then
        assertThat(findHobbyType.name).isEqualTo("취미 유형1")
    }

    @Test
    @DisplayName("MBTI 타입 여러 개로 취미 유형 조회")
    fun findByMbtiTypeIsIn() {
        // given
        val fitHobbyTypes1 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType1 = HobbyType(
            "취미 유형1", "취미 유형 상세정보", "https://example.com", "INTJ", fitHobbyTypes1
        )
        val fitHobbyTypes2 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType2 = HobbyType(
            "취미 유형2", "취미 유형 상세정보", "https://example.com", "ENFP", fitHobbyTypes2
        )
        val fitHobbyTypes3 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType3 = HobbyType(
            "취미 유형3", "취미 유형 상세정보", "https://example.com", "ENTP", fitHobbyTypes3
        )
        hobbyTypeRepository.save(hobbyType1)
        hobbyTypeRepository.save(hobbyType2)
        hobbyTypeRepository.save(hobbyType3)

        // when
        val findHobbyTypes = hobbyTypeRepository.findByMbtiTypeIsIn(listOf("INTJ", "ENFP", "ENTP"))

        // then
        assertThat(findHobbyTypes.size).isSameAs(3)
        assertThat(findHobbyTypes.find { hobbyType -> hobbyType.name == "취미 유형2" }?.mbtiType).isEqualTo("ENFP")
    }

    private fun createFitHobbyTypes(mbtiTypes: List<String>): MutableSet<FitHobbyType> {
        return mutableSetOf(
            FitHobbyType(mbtiTypes[0], 1),
            FitHobbyType(mbtiTypes[1], 2),
            FitHobbyType(mbtiTypes[2], 3)
        )
    }

}
