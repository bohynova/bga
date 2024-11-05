package by.starychonak.bga.service.impl

import by.starychonak.bga.dto.LoginUserDto
import by.starychonak.bga.dto.RegisterUserDto
import by.starychonak.bga.dto.UserDto
import by.starychonak.bga.entity.User
import by.starychonak.bga.mapper.toUser
import by.starychonak.bga.mapper.toUserDto
import by.starychonak.bga.repository.UserRepository
import by.starychonak.bga.repository.extensions.checkUniqueEmail
import by.starychonak.bga.repository.extensions.checkUniqueUsername
import by.starychonak.bga.repository.extensions.findByUserNameOrThrow
import by.starychonak.bga.security.JwtTokenProvider
import by.starychonak.bga.service.RoleService
import by.starychonak.bga.service.UserService
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleService: RoleService,
    @Lazy private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : UserService {

    override fun loadUserByUsername(username: String): User =
        userRepository.findByUserNameOrThrow(username)

    override fun register(user: RegisterUserDto): UserDto {
        userRepository.checkUniqueUsername(user.username)
        userRepository.checkUniqueEmail(user.email)

        val roleId = roleService.getDefaultRole()?.id!!
        return userRepository.save(
            user.toUser(
                passwordEncoder.encode(user.password),
                roleId,
            )
        ).toUserDto()
    }

    override fun getToken(loginUserDto: LoginUserDto): String {
        val user = loadUserByUsername(loginUserDto.username)
        return if (passwordEncoder.matches(loginUserDto.password, user.password)) {
            jwtTokenProvider.createToken(user.username, user.authorities.toSet())
        } else throw Exception("Invalid password")
    }
}