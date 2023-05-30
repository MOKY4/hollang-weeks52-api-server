package swyg.hollang.repository.answer

import org.springframework.data.jpa.repository.JpaRepository
import swyg.hollang.entity.Answer

interface AnswerJpaRepository: JpaRepository<Answer, Long> {

}
