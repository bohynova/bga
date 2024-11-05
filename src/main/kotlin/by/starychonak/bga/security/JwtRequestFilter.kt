package by.starychonak.bga.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collectors

@Component
class JwtRequestFilter(
    @Autowired
    val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwt: String? = null
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7)
            try {
                username = jwtTokenProvider.getUsername(jwt)
            } catch (e: ExpiredJwtException) {
                logger.debug("Jwt token expired")
            } catch (e: SignatureException) {
                logger.debug("Incorrect signature")
            }
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null && jwt != null) {
            val token = UsernamePasswordAuthenticationToken(
                username,
                null,
                jwtTokenProvider.getRoles(jwt).stream().map {
                    SimpleGrantedAuthority(it)
                }.collect(Collectors.toList())
            )
            SecurityContextHolder.getContext().authentication = token
        }
        filterChain.doFilter(request, response)
    }
}