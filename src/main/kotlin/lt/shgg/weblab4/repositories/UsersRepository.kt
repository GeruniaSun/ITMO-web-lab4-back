package lt.shgg.weblab4.repositories

import lt.shgg.weblab4.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UsersRepository : JpaRepository<User, Long> {
    fun findByName(username: String?): User?
    fun existsByName(username: String?): Boolean
}
