package by.starychonak.bga.service

import by.starychonak.bga.entity.Role

interface RoleService {
    fun create(name: String): Role
    fun generate(): List<Role>
    fun getDefaultRole(): Role?
}