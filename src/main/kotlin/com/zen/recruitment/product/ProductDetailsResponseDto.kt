package com.zen.recruitment.product

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

data class ProductDetailsResponseDto(
        val id: UUID,
        val name: String,
        val description: String,
        val price: BigDecimal
) {
    companion object {
        fun from(product: Product): ProductDetailsResponseDto {
            return ProductDetailsResponseDto(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price.setScale(2, RoundingMode.HALF_UP)
            )
        }
    }
}
