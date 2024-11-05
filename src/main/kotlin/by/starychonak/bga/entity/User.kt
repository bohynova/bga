package by.starychonak.bga.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @Column(name = "id", columnDefinition = "BIGINT")
    var id: Long? = null,

    @Column(name = "name", columnDefinition = "VARCHAR(225)", nullable = false)
    var name: String,

    @Column(name = "username", columnDefinition = "VARCHAR(50)", nullable = false)
    @get:JvmName("getUserName")
    var username: String,

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    var email: String,

    @Column(name = "password_hash", columnDefinition = "VARCHAR(255)", nullable = false)
    var passwordHash: String,

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP(6)", nullable = false)
    var registrationDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "phone", columnDefinition = "VARCHAR(15)", nullable = false)
    var phone: String,

    @Column(name = "role_id", columnDefinition = "BIGINT", nullable = false)
    var roleId: Long,

) : UserDetails {
    @ManyToOne
    @JoinColumn(name = "role_id", columnDefinition = "BIGINT", nullable = false, insertable = false, updatable = false)
    lateinit var role: Role

    override fun getAuthorities(): List<Role> {
        return listOf(role)
    }

    override fun getPassword(): String {
        return passwordHash
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}