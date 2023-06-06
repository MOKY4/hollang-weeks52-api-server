package swyg.hollang.repository

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.*
import swyg.hollang.repository.testresponsedetail.TestResponseDetailRepository

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestResponseDetailRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testResponseDetailRepository: TestResponseDetailRepository
) {

    @BeforeEach
    fun beforeEach() {
        val questions: MutableSet<Question> = mutableSetOf()
        for(i in 1..12){
            val answers: MutableSet<Answer> = mutableSetOf()
            for(j in 1..2){
                val answer = Answer(j.toLong(), "질문 $i 답변 $j")
                answers.add(answer)
            }

            val question = Question(i.toLong(),"질문 $i", answers)
            questions.add(question)
        }

        val test = Test(1, questions)
        em.persist(test)
    }

//    @Test
//    fun save() {
//        //given
//        val savedUser = User("쨈")
//        em.persist(savedUser)
//        val savedTestResponse = TestResponse(savedUser)
//        em.persist(savedTestResponse)
//        val findAnswers = em.createQuery(
//            "select a from Answer a " +
//                    "where a.number = :answerNumber " +
//                    "and a.question.number = :questionNumber " +
//                    "and a.question.test.version = :testVersion"
//        )
//            .setParameter("answerNumber", 2)
//            .setParameter("questionNumber", 1)
//            .setParameter("testVersion", 1)
//            .resultList as MutableList<Answer>
//
//        //when
//        val savedTestResponseDetail = testResponseDetailRepository
//            .save(TestResponseDetail(savedTestResponse, findAnswers[0]))
//
//        //then
//        assertThat(savedTestResponseDetail.testResponse).isEqualTo(savedTestResponse)
//        assertThat(savedTestResponseDetail.answer).isEqualTo(findAnswers[0])
//        assertThat(savedTestResponseDetail.answer.content).isEqualTo("질문 1 답변 2")
//        assertThat(savedTestResponseDetail.answer.question!!.content).isEqualTo("질문 1")
//    }
}
