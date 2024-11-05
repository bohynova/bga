package by.starychonak.bga.service

import by.starychonak.bga.dto.LoginUserDto
import by.starychonak.bga.dto.RegisterUserDto
import by.starychonak.bga.dto.UserDto
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService: UserDetailsService {
    fun register(user: RegisterUserDto): UserDto
    fun getToken(loginUserDto: LoginUserDto): String
}