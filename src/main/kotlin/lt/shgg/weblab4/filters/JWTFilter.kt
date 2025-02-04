package lt.shgg.weblab4.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lt.shgg.weblab4.services.JWTService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.logging.Logger


@Component
class JWTFilter(
    @Autowired
    private val jwtService: JWTService,

    @Autowired
    private val userDetailsService: UserDetailsService
): OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if("OPTIONS".equals(request.method, ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
            return
        }

        val logger: Logger = Logger.getLogger("JWTFilter")
        val authorization = request.getHeader("Authorization")

        var jwtToken: String? = null
        var username: String? = null

        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7)
            username = jwtService.extractUsername(jwtToken)
            logger.info("привет, это я жэвэтэфильтр, тут некий $username")
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)

            if (jwtToken != null && jwtService.validateToken(jwtToken)) {
                val authentication =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }
}