package swyg.hollang.repository.recommendationhobby

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import swyg.hollang.entity.RecommendationHobby
import java.time.ZonedDateTime

@Repository
class RecommendationHobbyRepositoryImpl(
    private val recommendationHobbyJpaRepository: RecommendationHobbyJpaRepository,
    private val jdbcTemplate: JdbcTemplate)
    : RecommendationHobbyRepository {

    override fun save(recommendationHobby: RecommendationHobby): RecommendationHobby {
        return recommendationHobbyJpaRepository.save(recommendationHobby)
    }

    override fun batchInsert(recommendationHobbies: List<RecommendationHobby>) : Int {
        val batchSize = 50
        return jdbcTemplate.batchUpdate(
            "insert into recommendation_hobby(recommendation_id, hobby_id, created_at, updated_at) values (?, ?, ?, ?)",
            recommendationHobbies, batchSize
        ) { ps, recommendationHobby ->
            ps.setLong(1, recommendationHobby.recommendation.id!!)
            ps.setLong(2, recommendationHobby.hobby.id!!)
            ps.setObject(3, ZonedDateTime.now())
            ps.setObject(4, ZonedDateTime.now())
        }.size
    }
}
