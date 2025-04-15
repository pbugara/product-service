package com.zen.recruitment

import com.zen.recruitment.product.Product
import com.zen.recruitment.product.ProductDetailsResponseDto
import java.math.BigDecimal
import java.util.*

class TestDataHelper {

    companion object {

        val productId: UUID = UUID.randomUUID()
        const val productName: String = "Sample Product"
        const val productDescription: String = "This is a sample product description."
        val productPrice: BigDecimal = BigDecimal("19.99")

        fun getProduct(): Product {
            return Product(
                id = productId,
                name = productName,
                description = productDescription,
                price = productPrice
            )
        }

        fun getProductDetailsResponseDto(): ProductDetailsResponseDto {
            return ProductDetailsResponseDto(
                id = productId,
                name = productName,
                description = productDescription,
                price = productPrice
            )
        }
    }
}
