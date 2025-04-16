package com.zen.recruitment.product

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class ProductService(val productRepository: ProductRepository) {

    /**
     * Get product details by ID
     *
     * @param id UUID of the product
     * @return Product
     * @throws ProductException if product not found
     */
    fun getProductById(id: UUID): Product {
        return productRepository.findById(id)
                .orElseThrow { ProductException.notFound(id) }
    }

    /**
     * Get product price by product ID
     *
     * @param id UUID of the product
     * @return BigDecimal price of the product
     * @throws ProductException if product not found
     */
    fun getProductPriceById(id: UUID): BigDecimal {
        return productRepository.findPriceById(id)?.getPrice()
                ?: throw ProductException.notFound(id)
    }
}
