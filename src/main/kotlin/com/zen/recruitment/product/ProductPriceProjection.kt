package com.zen.recruitment.product

import java.math.BigDecimal

interface ProductPriceProjection {
    fun getPrice(): BigDecimal
}
