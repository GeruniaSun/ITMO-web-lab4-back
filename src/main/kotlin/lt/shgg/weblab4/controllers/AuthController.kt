package lt.shgg.weblab4.controllers

import lt.shgg.weblab4.models.User
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import lt.shgg.weblab4.services.AuthService
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = ["http://localhost:1488"])
class AuthController @Autowired constructor(
    private val authService: AuthService
){
    private val logger: Logger = Logger.getLogger("AuthController")

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<Map<String, String>> {
        return processToken(authService.login(user))
    }

    @PostMapping("/register")
    @Throws(BadRequestException::class)
    fun register(@RequestBody user: User): ResponseEntity<Map<String, String>> {
        return processToken(authService.register(user))
    }

    private fun processToken(token: String): ResponseEntity<Map<String, String>> {
        val response: MutableMap<String, String> = HashMap()

        response["token"] = token

        return ResponseEntity.ok(response)
    }
}