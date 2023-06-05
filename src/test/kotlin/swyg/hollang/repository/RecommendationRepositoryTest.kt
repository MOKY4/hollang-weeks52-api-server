package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.HobbyType
import swyg.hollang.entity.Recommendation
import swyg.hollang.entity.TestResponse
import swyg.hollang.entity.User
import swyg.hollang.repository.recommendation.RecommendationRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class RecommendationRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val recommendationRepository: RecommendationRepository
) {

    @Test
    fun save() {
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)
        val createdTestResponse = TestResponse(createdUser)
        em.persist(createdTestResponse)

        val hobbyType = HobbyType("홀랑 유형 1", "test", "test@url.com",
            "ENFP", mutableListOf("ENTP", "INFP", "ESTJ"))
        em.persist(hobbyType)

        val mbtiScores = listOf(mapOf("scoreE" to 3, "scoreN" to 1, "scoreF" to 3, "scoreJ" to 2))

        //when
        val recommendation = Recommendation(createdTestResponse, hobbyType, mbtiScores)
        val savedRecommendation = recommendationRepository.save(recommendation)
        //then
        val findRecommendation = em.createQuery(
            "select r from Recommendation r where r.testResponse = :testResponse",
            Recommendation::class.java
        ).setParameter("testResponse", createdTestResponse)
            .resultList

        assertThat(savedRecommendation).isEqualTo(findRecommendation[0])
        assertThat(findRecommendation[0].hobbyType.name).isEqualTo("홀랑 유형 1")
        assertThat(findRecommendation[0].hobbyType.mbtiType).isEqualTo("ENFP")
        assertThat(findRecommendation[0].hobbyType.fitHobbyTypes[0]).isEqualTo("ENTP")
    }
}
