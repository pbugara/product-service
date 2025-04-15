package com.zen.recruitment.product

import java.math.BigDecimal
import java.util.*

data class ProductPriceResponseDto(val totalPrice: BigDecimal,
                                   val productId: UUID,
                                   val quantity: Int)
