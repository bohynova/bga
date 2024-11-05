package by.starychonak.bga.dto

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Pattern

data class RegisterUserDto (
    val username: String,
    val password: String,
    val confirmPassword: String,
    val email: String,
    val name: String,
    @field:Pattern(regexp = "^\\d{10}\$")
    val phone: String
) {
    @AssertTrue(message = "Passwords are not the same")
    fun isPasswordsValid() = password == confirmPassword
}