package swyg.hollang.repository.survey

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.Survey

interface SurveyJpaRepository : JpaRepository<Survey, Long> {
}
