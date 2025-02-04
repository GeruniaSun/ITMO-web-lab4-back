package lt.shgg.weblab4.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "points")
class Point (
    @Column(nullable = false)
    var x: Double = 0.0,

    @Column(nullable = false)
    var y: Double = 0.0,

    @Column(nullable = false)
    var r: Double = 0.0,

    @Column(nullable = false)
    var hit: Boolean = false,

    @Column(nullable = false)
    val datetime: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    public var user: User? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 1

    override fun toString(): String {
        return "x: %.2f, y: %.2f, r: %.2f".format(x, y, r)
    }
}