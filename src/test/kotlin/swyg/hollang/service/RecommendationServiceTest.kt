package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateRecommendationResultResponse
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class RecommendationServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val recommendationService: RecommendationService
) {

    @BeforeEach
    fun beforeEach() {

        for (i in 1..3) {
            val hobby = Hobby(
                "홀랑 $i",
                "default",
                "홀랑 $i 상세정보",
                "https://example.com/hollang$i.png"
            )
            em.persist(hobby)
        }
    }

//    @Test
//    fun save() {
//        //given
//        val createdUser = User("쨈")
//        em.persist(createdUser)
//        val createdTestResponse = TestResponse(createdUser)
//        em.persist(createdTestResponse)
//
//        val hobbyType = HobbyType("홀랑 유형 1", "test", "test@url.com",
//            "ENFP", mutableListOf("ENTP", "INFP", "ESTJ"))
//        em.persist(hobbyType)
//
//        val mbtiScores = listOf(mapOf("scoreE" to 3, "scoreN" to 1, "scoreF" to 3, "scoreJ" to 2))
//
//        //when
//        val createdRecommendation = recommendationService.save(createdTestResponse, hobbyType, mbtiScores)
//        //then
//        val findRecommendation = em.createQuery(
//            "select r from Recommendation r where r.testResponse = :testResponse",
//            Recommendation::class.java
//        ).setParameter("testResponse", createdTestResponse)
//            .resultList
//        assertThat(createdRecommendation.id).isSameAs(findRecommendation[0].id)
//        assertThat(createdRecommendation.hobbyType.name).isEqualTo(findRecommendation[0].hobbyType.name)
//    }
}
