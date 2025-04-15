package com.zen.recruitment.product

import com.zen.recruitment.discount.DiscountFacade
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProductFacade(
        val productService: ProductService,
        val discountFacade: DiscountFacade
) {

    /**
     * Get product details by ID
     *
     * @param id UUID of the product
     * @return ProductDetailsResponseDto
     */
    fun getProductById(id: UUID): ProductDetailsResponseDto {
        val product = productService.getProductById(id)
        return ProductDetailsResponseDto.from(product)
    }

    fun getProductPriceWithDiscounts(id: UUID, quantity: Int): ProductPriceResponseDto {
        val productPrice = productService.getProductPriceById(id)
        val discountedPrice = discountFacade.calculateDiscountedPrice(productPrice, quantity)
        return ProductPriceResponseDto(
                productId = id,
                totalPrice = discountedPrice,
                quantity = quantity
        )
    }
}
