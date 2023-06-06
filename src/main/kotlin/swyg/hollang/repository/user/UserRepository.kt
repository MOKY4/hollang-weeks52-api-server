package swyg.hollang.repository.user

import swyg.hollang.entity.User

interface UserRepository {

    fun save(user: User): User
}
