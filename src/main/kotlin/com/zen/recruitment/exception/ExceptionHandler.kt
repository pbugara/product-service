package com.zen.recruitment.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice

class ExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(exception: BaseException): ProblemDetail {
        log.error("Custom error occurred", exception)
        return ProblemDetail.forStatusAndDetail(exception.status, exception.message)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleInvalidUuid(ex: MethodArgumentTypeMismatchException, request: HttpServletRequest): ProblemDetail {
        log.error("Invalid argument type: ${ex.name} in request: ${request.requestURI}", ex)
        val problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        problem.title = "Invalid request"
        problem.detail = "Invalid method argument type"
        return problem
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(exception: Exception): ProblemDetail {
        log.error("An unexpected error occurred", exception)
        return ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred"
        )
    }
}
