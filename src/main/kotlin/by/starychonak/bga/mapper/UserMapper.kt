package by.starychonak.bga.mapper

import by.starychonak.bga.dto.RegisterUserDto
import by.starychonak.bga.dto.UserDto
import by.starychonak.bga.entity.User
import by.starychonak.bga.enums.Roles
import java.time.LocalDateTime

fun RegisterUserDto.toUser(
    passwordHash: String,
    role: Long
) = User(
    name = name,
    username = username,
    email = email,
    passwordHash = passwordHash,
    phone = phone,
    registrationDate = LocalDateTime.now(),
    roleId = role
)

fun User.toUserDto() = UserDto(
    id = id,
    name = name,
    username = username,
    email = email,
    phone = phone,
    registrationDate = registrationDate,
    role = Roles.findRoleByValue(roleId)
)