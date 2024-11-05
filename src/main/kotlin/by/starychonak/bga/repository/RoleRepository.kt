package by.starychonak.bga.repository

import by.starychonak.bga.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByName(name: String): Role?
}