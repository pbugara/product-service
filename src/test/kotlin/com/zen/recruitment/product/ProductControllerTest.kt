package com.zen.recruitment.product

import com.zen.recruitment.TestDataHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class ProductControllerTest {

    private val productFacade = mock<ProductFacade>()

    private val uut = ProductController(productFacade)

    @Test
    fun `getProductById should return product details`() {
        // Given
        val productId = UUID.randomUUID()
        val expectedProductDetails = TestDataHelper.getProductDetailsResponseDto()
        whenever(productFacade.getProductById(productId)).thenReturn(expectedProductDetails)

        // When
        val response = uut.getProductById(productId)

        // Then
        assertNotNull(response)
        assertEquals(expectedProductDetails, response)
        verify(productFacade).getProductById(productId)
    }
}
