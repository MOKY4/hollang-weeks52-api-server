package swyg.hollang.service

import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.User

@Transactional
@SpringBootTest
@ActiveProfiles(value = ["test"])
class UserServiceTest(
    @Autowired private val em: EntityManager,
    @Autowired private val userService: UserService
) {

    @Test
    fun save() {
        //given
        val user = User("쨈")

        //when
        val createdUser = userService.createUser(user)

        val findUser = em.createQuery("select u from User u where u.name = :name", User::class.java)
            .setParameter("name", "쨈")
            .resultList

        //then
        assertThat(createdUser).isEqualTo(findUser[0])
    }
}
