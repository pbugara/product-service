package com.zen.recruitment.product

import com.zen.recruitment.TestDataHelper
import com.zen.recruitment.TestDataHelper.Companion.productId
import com.zen.recruitment.TestDataHelper.Companion.quantity
import com.zen.recruitment.discount.DiscountFacade
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ProductFacadeTest {

    private val productService = mock<ProductService>()

    private val discountFacade = mock<DiscountFacade>()

    private val uut = ProductFacade(productService, discountFacade)

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

    @Test
    fun `getProductPriceWithDiscounts should return product price with discounts`() {
        // Given
        val expectedProductPrice = TestDataHelper.productPrice
        val expectedDiscountedPrice = TestDataHelper.totalPrice
        val expectedProductPriceWithDiscounts = TestDataHelper.getProductPriceWithDiscountsResponseDto()
        whenever(productService.getProductPriceById(productId)).thenReturn(expectedProductPrice)
        whenever(discountFacade.calculateDiscountedPrice(expectedProductPrice, quantity)).thenReturn(expectedDiscountedPrice)

        // When
        val response = uut.getProductPriceWithDiscounts(productId, quantity)

        // Then
        assertNotNull(response)
        assertEquals(expectedProductPriceWithDiscounts, response)
    }
}
