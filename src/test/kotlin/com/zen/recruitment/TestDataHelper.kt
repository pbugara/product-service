package com.zen.recruitment

import com.zen.recruitment.product.Product
import com.zen.recruitment.product.ProductDetailsResponseDto
import com.zen.recruitment.product.ProductPriceProjection
import com.zen.recruitment.product.ProductPriceResponseDto
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class TestDataHelper {

    companion object {

        val productId: UUID = UUID.randomUUID()
        const val productName: String = "Sample Product"
        const val productDescription: String = "This is a sample product description."
        val productPrice: BigDecimal = BigDecimal("100")
        val quantity: Int = 10
        val totalPrice: BigDecimal = productPrice.multiply(BigDecimal(quantity))
        val discountedPrice = totalPrice.divide(BigDecimal(2)).setScale(2, RoundingMode.HALF_UP)

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

        fun getProductPriceWithDiscountsResponseDto(): ProductPriceResponseDto {
            return ProductPriceResponseDto(
                productId = productId,
                quantity = quantity,
                totalPrice = totalPrice
            )
        }

        fun getPriceProjection() : ProductPriceProjection {
            return TestPriceProjection(productPrice)
        }

        data class TestPriceProjection(
                private val price: BigDecimal
        ) : ProductPriceProjection {
            override fun getPrice(): BigDecimal = price
        }
    }
}
