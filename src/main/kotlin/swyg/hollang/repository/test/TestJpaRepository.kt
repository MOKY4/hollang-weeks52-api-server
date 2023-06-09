package swyg.hollang.repository.test

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import swyg.hollang.entity.Test

interface TestJpaRepository: JpaRepository<Test, Long> {

    @Query("select t from Test t " +
            "join fetch t.questions q " +
            "join fetch q.answers a " +
            "where t.version = :version")
    fun findByVersionWithQuestions(version: Int): Test?
}
