package br.ufpr.fernanda.delivery

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(val message: String)

@RestControllerAdvice
class ExceptionHandlers{
    
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ex.message!!))

    @ExceptionHandler(InvalidOrderException::class)
    fun handleInvalidOrder(ex: InvalidOrderException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ex.message!!))
    
    @ExceptionHandler(InvalidStatusTransitionException::class)
    fun handleInvalidStatusTransition(ex: InvalidStatusTransitionException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(ex.message!!))
}