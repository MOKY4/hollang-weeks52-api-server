package swyg.hollang.repository.user

import org.springframework.stereotype.Repository
import swyg.hollang.entity.User

@Repository
class UserRepositoryImpl(private val userJpaRepository: UserJpaRepository): UserRepository {

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

}
