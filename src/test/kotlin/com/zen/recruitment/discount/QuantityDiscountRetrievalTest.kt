package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.QuantityDiscount
import com.zen.recruitment.discount.domain.QuantityDiscountConfigs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal

class QuantityDiscountRetrievalTest {

    private val discountService = mock<DiscountService>()
    private val uut = QuantityDiscountRetrieval(discountService)

    @Test
    fun `getDiscountValue should return 0 when no discount found`() {
        // Given
        whenever(discountService.getQuantityDiscount()).thenReturn(null)

        // When
        val result = uut.getDiscountValue(1)

        // Then
        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun `getDiscountValue should return discount value when found`() {
        // Given
        val discount = 5
        val expectedDiscount = BigDecimal(discount)
        val quantity = 3

        val quantityDiscountConfig = mock<QuantityDiscountConfigs>()
        whenever(quantityDiscountConfig.minQty).thenReturn(1)
        whenever(quantityDiscountConfig.maxQty).thenReturn(10)
        whenever(quantityDiscountConfig.percentage).thenReturn(discount)
        val quantityDiscount = QuantityDiscount(listOf(quantityDiscountConfig))

        whenever(discountService.getQuantityDiscount()).thenReturn(quantityDiscount)

        // When
        val result = uut.getDiscountValue(quantity)

        // Then
        assertEquals(expectedDiscount, result)
    }

    @Test
    fun `getDiscountValue should return ZERO value when quantity do not match range`() {
        // Given
        val discount = 5
        val quantity = 20

        val quantityDiscountConfig = mock<QuantityDiscountConfigs>()
        whenever(quantityDiscountConfig.minQty).thenReturn(1)
        whenever(quantityDiscountConfig.maxQty).thenReturn(10)
        whenever(quantityDiscountConfig.percentage).thenReturn(discount)
        val quantityDiscount = QuantityDiscount(listOf(quantityDiscountConfig))

        whenever(discountService.getQuantityDiscount()).thenReturn(quantityDiscount)

        // When
        val result = uut.getDiscountValue(quantity)

        // Then
        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun `getDiscountValue should return discount value when quantity match infinite range`() {
        // Given
        val discount = 5
        val quantity = 20
        val expectedDiscount = BigDecimal(discount)

        val quantityDiscountConfig = mock<QuantityDiscountConfigs>()
        whenever(quantityDiscountConfig.minQty).thenReturn(1)
        whenever(quantityDiscountConfig.maxQty).thenReturn(null)
        whenever(quantityDiscountConfig.percentage).thenReturn(discount)
        val quantityDiscount = QuantityDiscount(listOf(quantityDiscountConfig))

        whenever(discountService.getQuantityDiscount()).thenReturn(quantityDiscount)

        // When
        val result = uut.getDiscountValue(quantity)

        // Then
        assertEquals(expectedDiscount, result)
    }
}
