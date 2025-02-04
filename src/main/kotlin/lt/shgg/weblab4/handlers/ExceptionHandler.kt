package lt.shgg.weblab4.handlers

import org.apache.coyote.BadRequestException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException?, request: WebRequest?): ResponseEntity<String> {
        return ResponseEntity.badRequest().body("Ну у вас запрос какой-то дурацкий")
    }
}
