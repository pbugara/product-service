package com.zen.recruitment.product

import com.zen.recruitment.TestDataHelper
import com.zen.recruitment.TestDataHelper.Companion.productId
import com.zen.recruitment.TestDataHelper.Companion.quantity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ProductControllerTest {

    private val productFacade = mock<ProductFacade>()

    private val uut = ProductController(productFacade)

    @Test
    fun `getProductById should return product details`() {
        // Given
        val expectedProductDetails = TestDataHelper.getProductDetailsResponseDto()
        whenever(productFacade.getProductById(productId)).thenReturn(expectedProductDetails)

        // When
        val response = uut.getProductById(productId)

        // Then
        assertNotNull(response)
        assertEquals(expectedProductDetails, response)
        verify(productFacade).getProductById(productId)
    }

    @Test
    fun `getProductPriceWithDiscounts should return product price with discounts`() {
        // Given
        val expectedProductPriceWithDiscounts = TestDataHelper.getProductPriceWithDiscountsResponseDto()
        whenever(productFacade.getProductPriceWithDiscounts(productId, quantity)).thenReturn(expectedProductPriceWithDiscounts)

        // When
        val response = uut.getProductPriceWithDiscounts(productId, quantity)

        // Then
        assertNotNull(response)
        assertEquals(expectedProductPriceWithDiscounts, response)
        verify(productFacade).getProductPriceWithDiscounts(productId, quantity)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1])
    fun `getProductPriceWithDiscounts should throw exception when quantity is less than 1`(invalidQuantity: Int) {
        // When & Then
        val exception = assertThrows<ProductException> {
            uut.getProductPriceWithDiscounts(productId, invalidQuantity)
        }
        assertEquals("Quantity must be greater than 0", exception.message)
    }
}
