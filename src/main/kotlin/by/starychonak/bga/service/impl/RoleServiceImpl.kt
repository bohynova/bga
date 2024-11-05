package by.starychonak.bga.service.impl

import by.starychonak.bga.entity.Role
import by.starychonak.bga.enums.Roles
import by.starychonak.bga.repository.RoleRepository
import by.starychonak.bga.service.RoleService
import by.starychonak.bga.utils.CACHE
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository
): RoleService {

    override fun create(name: String) =
        roleRepository.save(Role(name = name))

    override fun generate(): List<Role> = Roles.values().map { Role(name = it.roleName) }
        .apply { roleRepository.saveAll(this) }

    @Cacheable(CACHE.CACHE_DEFAULT_ROLE)
    override fun getDefaultRole() = roleRepository.findByName(Roles.ROLE_USER.roleName)
        ?: throw Exception("Cannot default role user")
}