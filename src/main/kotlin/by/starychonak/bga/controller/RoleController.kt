package by.starychonak.bga.controller

import by.starychonak.bga.service.RoleService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/role"], produces = [MediaType.APPLICATION_JSON_VALUE])
class RoleController(
    private val roleService: RoleService
) {

    @GetMapping("/generate")
    fun generate() = ResponseEntity.ok(roleService.generate())
}