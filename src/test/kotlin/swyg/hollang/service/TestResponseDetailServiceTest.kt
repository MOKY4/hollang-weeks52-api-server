package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class TestResponseDetailServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val testResponseDetailService: TestResponseDetailService,
    @Autowired private val answerService: AnswerService
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

    @Test
    fun createTestResponseDetail() {
        //given
        val createdUser = User("쨈")
        em.persist(createdUser)
        val createdTestResponse = TestResponse(createdUser)
        em.persist(createdTestResponse)
        em.flush()

        val createTestResponseDetailRequests = mutableListOf(
            CreateTestResponseDetailRequest(1, 1),
            CreateTestResponseDetailRequest(2, 2),
            CreateTestResponseDetailRequest(3, 1),
            CreateTestResponseDetailRequest(4, 1),
            CreateTestResponseDetailRequest(5, 2),
            CreateTestResponseDetailRequest(6, 1),
            CreateTestResponseDetailRequest(7, 2),
            CreateTestResponseDetailRequest(8, 1),
            CreateTestResponseDetailRequest(9, 1),
            CreateTestResponseDetailRequest(10, 2),
            CreateTestResponseDetailRequest(11, 2),
            CreateTestResponseDetailRequest(12, 2),
        )

        val questionAnswerPairs = createTestResponseDetailRequests.map { it.questionNumber to it.answerNumber }
        val answers = answerService
            .getAnswersByQuestionAnswerPairsByTestVersion(questionAnswerPairs, 1)

        //when
        testResponseDetailService.createTestResponseDetails(createdTestResponse, answers)

        //then
        val findTestResponseDetails =
            em.createQuery("select trd from TestResponseDetail trd where trd.testResponse.id = :testResponseId",
                TestResponseDetail::class.java)
                .setParameter("testResponseId", createdTestResponse.id)
                .resultList

        assertThat(findTestResponseDetails.size).isSameAs(12)
        assertThat(findTestResponseDetails[0].testResponse.user.name).isEqualTo(createdUser.name)
    }
}
