package swyg.hollang.service.hobbytype

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.FitHobbyType
import swyg.hollang.entity.HobbyType
import java.lang.IllegalArgumentException

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
internal class HobbyTypeServiceTest(
    @Autowired val em: EntityManager,
    @Autowired val hobbyTypeService: HobbyTypeService
) {

    @Test
    @DisplayName("MBTI 타입으로 취미 유형 조회")
    fun getByMbtiType() {
        // given
        val fitHobbyTypes = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val mbtiType = "INTJ"
        val hobbyType = HobbyType(
            "취미 유형1", "취미 유형 상세정보", "https://example.com", mbtiType, fitHobbyTypes
        )
        em.persist(hobbyType)

        // when
        val findHobbyTypes = hobbyTypeService.getByMbtiType(mbtiType)

        // then
        assertThat(findHobbyTypes.name).isEqualTo(hobbyType.name)
    }

    @Test
    @DisplayName("MBTI 타입들로 취미 유형들 조회")
    fun getAllByMbtiTypeIsIn() {
        // given
        val validMbtiTypes = listOf("INTJ", "ENFP", "ENTP")
        val invalidMbtiTypes = listOf("INTP", "ENFJ", "ESTP")

        val fitHobbyTypes1 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType1 = HobbyType(
            "취미 유형1", "취미 유형 상세정보", "https://example.com", validMbtiTypes[0], fitHobbyTypes1
        )
        val fitHobbyTypes2 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType2 = HobbyType(
            "취미 유형2", "취미 유형 상세정보", "https://example.com", validMbtiTypes[1], fitHobbyTypes2
        )
        val fitHobbyTypes3 = createFitHobbyTypes(listOf("ENFP", "ENTP", "INTP"))
        val hobbyType3 = HobbyType(
            "취미 유형3", "취미 유형 상세정보", "https://example.com", validMbtiTypes[2], fitHobbyTypes3
        )
        em.persist(hobbyType1)
        em.persist(hobbyType2)
        em.persist(hobbyType3)

        // when
        val findHobbyTypes = hobbyTypeService.getAllByMbtiTypeIsIn(validMbtiTypes)

        // then
        assertThat(findHobbyTypes.size).isSameAs(3)
        assertThatThrownBy {
            hobbyTypeService.getAllByMbtiTypeIsIn(invalidMbtiTypes)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    private fun createFitHobbyTypes(mbtiTypes: List<String>): MutableSet<FitHobbyType> {
        return mutableSetOf(
            FitHobbyType(mbtiTypes[0], 1),
            FitHobbyType(mbtiTypes[1], 2),
            FitHobbyType(mbtiTypes[2], 3)
        )
    }
}
