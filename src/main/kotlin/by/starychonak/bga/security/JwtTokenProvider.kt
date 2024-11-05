package by.starychonak.bga.security

import by.starychonak.bga.entity.Role
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Duration
) {

    fun createToken(username: String, roles: Set<Role>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] = roles.map { it.name }

        val now = Date()
        val validity = Date(now.time + expiration.toMillis())

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }

    fun getRoles(token: String): List<String> {
        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        return claims["roles"] as List<String>
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}