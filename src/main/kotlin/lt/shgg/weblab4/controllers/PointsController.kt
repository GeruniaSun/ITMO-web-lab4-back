package lt.shgg.weblab4.controllers

import lt.shgg.weblab4.models.Point
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import lt.shgg.weblab4.services.JWTService
import lt.shgg.weblab4.services.PointsService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Controller
import java.util.logging.Logger


@RestController
class PointsController @Autowired constructor(
    private val jwtService: JWTService,
    private val pointsService: PointsService
) {
    private val logger: Logger = Logger.getLogger("PointsController")

    @GetMapping("/page")
    fun getPage(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
                @RequestParam pageNumber: Int): ResponseEntity<Page<Point>?> {
        val jwt = token.substring(7)
        val username: String = jwtService.extractUsername(jwt)
        logger.info("$username листает табличку $pageNumber")

        return ResponseEntity.ok(pointsService.getUserPointsPage(username, pageNumber))
    }

    @PostMapping("/check")
    @Throws(BadRequestException::class)
    fun addPoint(
        @RequestBody point: Point,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<Point> {
        val jwt = token.substring(7)
        val username: String = jwtService.extractUsername(jwt)

        val checkedPoint: Point = pointsService.checkHit(point, username)
        logger.info("$username хочет выстрел: $checkedPoint")

        logger.info("щас как отошлю ${ResponseEntity.ok(checkedPoint)}")
        return ResponseEntity.ok(checkedPoint)
    }
}