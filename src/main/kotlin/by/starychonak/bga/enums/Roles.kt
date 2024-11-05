package by.starychonak.bga.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class Roles(@field:JsonValue val value: Long, val roleName: String) {
    ROLE_ADMIN(1, "admin"),
    ROLE_USER(2, "user");

    companion object {
        fun findRoleByValue(value: Long) =
            Roles.values().firstOrNull { it.value == value } ?: throw Exception("Cannot find role by value = $value")
    }
}