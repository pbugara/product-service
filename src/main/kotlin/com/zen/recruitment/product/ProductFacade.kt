package com.zen.recruitment.product

import org.springframework.stereotype.Component
import java.util.*

@Component
class ProductFacade(val productService: ProductService) {

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
}
