package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import swyg.hollang.repository.recommendation.RecommendationRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class RecommendationRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val recommendationRepository: RecommendationRepository
) {

//    @Test
//    fun save() {
//        //given
//        val createdUser = User("쨈")
//        val hobbyType = HobbyType("홀랑 유형 1", "test", "test@url.com",
//            "ENFP", mutableListOf("ENTP", "INFP", "ESTJ"))
//        val mbtiScores = listOf(mapOf("scoreE" to 3, "scoreN" to 1, "scoreF" to 3, "scoreJ" to 2))
//
//        val recommendationHobbies = mutableListOf<RecommendationHobby>()
//        for (i in 1..40) {
//            val hobby = Hobby(
//                "홀랑 $i",
//                "default",
//                "홀랑 $i 상세정보",
//                "https://example.com/hollang$i.png"
//            )
//            recommendationHobbies.add(RecommendationHobby(hobby))
//        }
//
//        val recommendation = Recommendation(hobbyType, mbtiScores, recommendationHobbies)
//        val createdTestResponse = TestResponse(createdUser, recommendation, )
//        em.persist(createdTestResponse)
//
//
//        em.persist(hobbyType)
//
//
//
//        //when
//        //then
//        val findRecommendation = em.createQuery(
//            "select r from Recommendation r where r.testResponse = :testResponse",
//            Recommendation::class.java
//        ).setParameter("testResponse", createdTestResponse)
//            .resultList
//
//        assertThat(savedRecommendation).isEqualTo(findRecommendation[0])
//        assertThat(findRecommendation[0].hobbyType.name).isEqualTo("홀랑 유형 1")
//        assertThat(findRecommendation[0].hobbyType.mbtiType).isEqualTo("ENFP")
//        assertThat(findRecommendation[0].hobbyType.fitHobbyTypes[0]).isEqualTo("ENTP")
//    }
}
