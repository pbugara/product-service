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
}
