package lt.shgg.weblab4.services

import lt.shgg.weblab4.models.User
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import lt.shgg.weblab4.repositories.UsersRepository
import java.util.logging.Logger


@Service
class AuthService @Autowired constructor(
    @Autowired
    private val passwordEncoder: PasswordEncoder,

    @Autowired
    private val jwtService: JWTService,

    @Autowired
    private val usersRepository: UsersRepository,

    @Autowired
    private val authenticationManager: AuthenticationManager,

    private val logger: Logger = Logger.getLogger("AuthService")
){
    @Throws(BadRequestException::class)
    fun register(newUser: User): String {
        if (usersRepository.existsByName(newUser.getUsername()))
            throw BadCredentialsException("Этот юзер уже смешарик")

        val user: User = User()
        user.name = newUser.name
        user.pass = passwordEncoder.encode(newUser.pass)
        user.role = User.Roles.USER
        usersRepository.save(user)

        logger.info("\nУ нас тут новенький\n" +
                "Имя: ${newUser.username}\nПаролик: ${passwordEncoder.encode(newUser.pass)}")

        return jwtService.generateToken(user.username)
    }

    @Throws(UsernameNotFoundException::class)
    fun login(presentUser: User): String {
        logger.info("к нам тут в дверь ломится некий ${presentUser.username}")

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                presentUser.username,
                presentUser.pass
            )
        )

        val user: User = usersRepository.findByName(presentUser.getUsername()) ?:
        throw UsernameNotFoundException("Таких не знаем")

        return jwtService.generateToken(user.getUsername())
    }
}