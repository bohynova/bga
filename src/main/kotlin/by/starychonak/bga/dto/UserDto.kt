package by.starychonak.bga.dto

import by.starychonak.bga.enums.Roles
import java.time.LocalDateTime

data class UserDto(
    val id: Long? = null,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val registrationDate: LocalDateTime? = null,
    val role: Roles? = null
)

