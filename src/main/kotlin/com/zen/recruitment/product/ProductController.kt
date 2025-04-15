package com.zen.recruitment.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(val productFacade: ProductFacade) {

    /**
     * Get product details by ID
     *
     * @param id UUID of the product
     * @return ProductDetailsResponseDto
     */
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: UUID): ProductDetailsResponseDto {
        return productFacade.getProductById(id)
    }

    /**
     * Get product price with discounts applied
     *
     * @param id UUID of the product
     * @param quantity Quantity of the product
     * @return ProductPriceResponseDto
     * @throws ProductException if the quantity is less than or equal to 0
     */
    @GetMapping("/{id}/quantity/{quantity}/price")
    fun getProductPriceWithDiscounts(@PathVariable id: UUID,
                                     @PathVariable quantity: Int): ProductPriceResponseDto {
        if (quantity <= 0) {
            throw ProductException.invalidQuantity()
        }
        return productFacade.getProductPriceWithDiscounts(id, quantity)
    }
}
