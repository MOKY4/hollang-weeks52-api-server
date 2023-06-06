package swyg.hollang.repository.recommendation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Recommendation

interface RecommendationJpaRepository: JpaRepository<Recommendation, Long> {

    @Query("select distinct r from Recommendation r " +
            "join fetch r.testResponse.user u " +
            "join fetch r.hobbyType ht " +
            "join fetch r.recommendationHobbies rh " +
            "left join fetch rh.hobby h " +
            "left join fetch rh.survey s " +
            "where r.id = :recommendationId ")
    fun findByIdWithUserAndHobbyTypeAndHobbiesAndSurvey(recommendationId: Long): Recommendation?

    @Query("select distinct r from Recommendation r " +
            "join fetch r.testResponse.user u " +
            "join fetch r.hobbyType ht " +
            "join fetch r.recommendationHobbies rh " +
            "left join fetch rh.hobby h " +
            "where r.id = :recommendationId")
    fun findByIdWithUserAndHobbyTypeAndHobbies(recommendationId: Long): Recommendation?
}
