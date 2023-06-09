package swyg.hollang.repository.recommendation

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Recommendation

interface RecommendationJpaRepository: JpaRepository<Recommendation, Long> {

    @Query("select distinct r from Recommendation r " +
            "where r.id = :recommendationId")
    @EntityGraph(
        attributePaths = [
            "user",
            "hobbyType",
            "hobbyType.fitHobbyTypes",
            "recommendationHobbies",
            "recommendationHobbies.hobby",
            "recommendationHobbies.survey"
        ]
    )
    fun findWithUserAndHobbyTypeAndHobbiesAndSurveyById(recommendationId: Long): Recommendation?

    @Query("select distinct r from Recommendation r " +
            "where r.id = :recommendationId")
    @EntityGraph(
        attributePaths = [
            "user",
            "hobbyType",
            "recommendationHobbies",
            "recommendationHobbies.hobby"
        ]
    )
    fun findWithUserAndHobbyTypeAndHobbiesById(recommendationId: Long): Recommendation?

    @Query("select distinct r from Recommendation r " +
            "where r.id = :recommendationId")
    @EntityGraph(
        attributePaths = [
            "recommendationHobbies",
            "recommendationHobbies.hobby"
        ]
    )
    fun findWithRecommendationHobbiesAndHobbiesById(recommendationId: Long): Recommendation?
}
