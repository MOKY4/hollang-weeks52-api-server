package swyg.hollang.repository.survey

import org.springframework.stereotype.Repository
import swyg.hollang.entity.Survey

@Repository
class SurveyRepositoryImpl(private val surveyJpaRepository: SurveyJpaRepository) : SurveyRepository {

    override fun save(survey: Survey): Survey {
        return surveyJpaRepository.save(survey)
    }
}
