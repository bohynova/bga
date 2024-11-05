package by.starychonak.bga.controller

import by.starychonak.bga.dto.LoginUserDto
import by.starychonak.bga.dto.RegisterUserDto
import by.starychonak.bga.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/user"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody @Valid userDto: RegisterUserDto): ResponseEntity<Any> {
        return ResponseEntity.ok(userService.register(userDto))
    }

    @PostMapping("/login")
    fun matchesPasswords(@RequestBody loginUserDto: LoginUserDto): ResponseEntity<Any> {
        val token = userService.getToken(loginUserDto)
        return ResponseEntity.ok().headers(
            HttpHeaders().apply {
                set(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
        ).build()
    }

}