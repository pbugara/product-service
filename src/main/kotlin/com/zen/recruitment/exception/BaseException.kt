package com.zen.recruitment.exception

import org.springframework.http.HttpStatus

abstract class BaseException(
        override val message: String,
        open val errorCode: String,
        open val status: HttpStatus,
) : RuntimeException(message)
