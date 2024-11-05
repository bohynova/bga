package by.starychonak.bga.repository

import by.starychonak.bga.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun existsUserByUsername(username: String): Boolean
    fun existsUserByEmail(email: String): Boolean
}