package swyg.hollang.repository.survey

import swyg.hollang.entity.Survey

interface SurveyRepository {

    fun save(survey: Survey): Survey

    fun saveAll(surveys: List<Survey>): List<Survey>
}
