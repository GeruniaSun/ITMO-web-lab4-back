package lt.shgg.weblab4.services

import lt.shgg.weblab4.mbeans.Counter
import lt.shgg.weblab4.mbeans.RatioStat
import lt.shgg.weblab4.models.Point
import lt.shgg.weblab4.models.User
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import lt.shgg.weblab4.repositories.PointsRepository
import lt.shgg.weblab4.repositories.UsersRepository
import lt.shgg.weblab4.utils.Calculator
import lt.shgg.weblab4.utils.Validator
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.yaml.snakeyaml.internal.Logger

@Service
class PointsService (
    @Autowired
    private var usersRepository: UsersRepository,

    @Autowired
    private var pointsRepository: PointsRepository,

    @Autowired
    private var counter: Counter,

    @Autowired
    private var ratioStat: RatioStat
) {
    private val logger: Logger = Logger.getLogger("PointsService")
    private val pageSize: Int = 10

    @Throws(BadRequestException::class)
    fun checkHit(point: Point, username: String): Point {
        if (!Validator.pointIsValid(point)) {
            logger.warn("$username прислал невалидную точечку $point")
            throw BadRequestException("ваша точка не валидна!!!")
        }
        val user: User? = usersRepository.findByName(username)
        point.user = user
        point.hit = Calculator.calcHit(point.x, point.y, point.r)
        pointsRepository.save(point)
        counter.updateAttempt(point.hit) // MBEAN MENTIONED!!!
        ratioStat.updateStat(point.hit) // MBEAN MENTIONED!!!

        return point
    }

    fun getUserPointsPage(username: String, pageNumber: Int): Page<Point>? {
        val user: User? = usersRepository.findByName(username)
        val pageable: Pageable = PageRequest.of(pageNumber, pageSize, Sort.by("datetime").descending())

        if (user != null) return pointsRepository.findAllByUser(user, pageable)
        return null
    }
}