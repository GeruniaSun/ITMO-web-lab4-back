package lt.shgg.weblab4.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
open class User(
    @Column(nullable = false, unique = true)
    open var name: String? = null,

    @Column(nullable = false)
    open var pass: String? = null,

    @Enumerated(EnumType.STRING)
    open var role: Roles? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    open val points: List<Point> = listOf()
) : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open var id: Long = 1

    open override fun getUsername(): String? = name

    open override fun getPassword(): String? = pass

    open override fun getAuthorities(): List<Roles?> = listOf(role)

    enum class Roles : GrantedAuthority {
        USER;

        override fun getAuthority(): String = "ROLE_$name"
    }
}