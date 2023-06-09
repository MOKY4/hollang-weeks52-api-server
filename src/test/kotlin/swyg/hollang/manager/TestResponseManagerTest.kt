package swyg.hollang.manager

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.dto.CreateTestResponseDetailRequest
import swyg.hollang.dto.CreateTestResponseRequest
import swyg.hollang.dto.CreateUserRequest
import swyg.hollang.entity.*

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
internal class TestResponseManagerTest(
    @Autowired val em: EntityManager,
    @Autowired val testResponseManager: TestResponseManager
) {

    @BeforeEach
    fun beforeEach() {
        val questions: MutableSet<Question> = mutableSetOf()
        for(i in 1..12){
            val answers: MutableSet<Answer> = mutableSetOf()
            for(j in 1..2){
                val answer = Answer(j, "질문 $i 답변 $j")
                answers.add(answer)
            }

            val question = Question(i,"질문 $i", answers)
            questions.add(question)
        }
        val test = Test(1, questions)
        em.persist(test)

        createHobbiesAndHobbyType()
    }

    @Test
    fun createTestResponse() {
        // given
        val createUserRequest = CreateUserRequest("쨈")
        val createTestResponseDetailRequests = listOf(
            CreateTestResponseDetailRequest(1, 2),
            CreateTestResponseDetailRequest(2, 2),
            CreateTestResponseDetailRequest(3, 2),
            CreateTestResponseDetailRequest(4, 2),
            CreateTestResponseDetailRequest(5, 1),
            CreateTestResponseDetailRequest(6, 1),
            CreateTestResponseDetailRequest(7, 2),
            CreateTestResponseDetailRequest(8, 1),
            CreateTestResponseDetailRequest(9, 2),
            CreateTestResponseDetailRequest(10, 1),
            CreateTestResponseDetailRequest(11, 2),
            CreateTestResponseDetailRequest(12, 2)
        )
        val createTestResponseRequest = CreateTestResponseRequest(createUserRequest, createTestResponseDetailRequests)

        // when
        val createTestResponseResponse =
            testResponseManager.createTestResponse(createTestResponseRequest = createTestResponseRequest)

        // then
        Assertions.assertThat(createTestResponseResponse.user.name).isEqualTo("쨈")
    }

    private fun createFitHobbyTypes(mbtiTypes: List<String>): MutableSet<FitHobbyType> {
        return mutableSetOf(
            FitHobbyType(mbtiTypes[0], 1),
            FitHobbyType(mbtiTypes[1], 2),
            FitHobbyType(mbtiTypes[2], 3)
        )
    }

    private fun createHobbiesAndHobbyType() {

        val fitHobbyTypes = createFitHobbyTypes(listOf("ESTJ", "ISFP", "ISTP"))
        val hobbyType = HobbyType(
            "나를 따르라형",
            "너도나도 팀이 되고 싶어 하는\\n마성의 매력 소유자!\\n숨길 수 없는 당신의 인싸력으로\\n취미를 짜릿하게 즐겨보아요",
            "https://example.com",
            "ESFJ",
            fitHobbyTypes
        )
        em.persist(hobbyType)

        val hobby1 = Hobby("어도비 일러스트레이트", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby2 = Hobby("스토리텔링", "취미 요약정보", "취미 상세정보", "https://example.com")
        val hobby3 = Hobby("어도비 인디자인", "취미 요약정보", "취미 상세정보", "https://example.com")
        em.persist(hobby1)
        em.persist(hobby2)
        em.persist(hobby3)
    }
}
