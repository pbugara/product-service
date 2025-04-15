package com.zen.recruitment.product

import com.zen.recruitment.TestDataHelper
import com.zen.recruitment.TestDataHelper.Companion.productId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ProductFacadeTest {

    private val productService = mock<ProductService>()

    private val uut = ProductFacade(productService)

    @Test
    fun `getProductById should return product details`() {
        // Given
        val expectedProduct = TestDataHelper.getProduct()
        val expectedProductDetails = TestDataHelper.getProductDetailsResponseDto()
        whenever(productService.getProductById(productId)).thenReturn(expectedProduct)

        // When
        val response = uut.getProductById(productId)

        // Then
        assertNotNull(response)
        assertEquals(expectedProductDetails, response)
    }
}
