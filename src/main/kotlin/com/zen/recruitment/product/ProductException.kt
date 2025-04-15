package com.zen.recruitment.product

import com.zen.recruitment.exception.BaseException
import org.springframework.http.HttpStatus
import java.util.*

class ProductException private constructor(
        override val message: String,
        override val status: HttpStatus
) : BaseException(message, status) {
    companion object {
        fun notFound(id: UUID): ProductException {
            return ProductException("Product with id $id not found", HttpStatus.NOT_FOUND)
        }
    }
}
