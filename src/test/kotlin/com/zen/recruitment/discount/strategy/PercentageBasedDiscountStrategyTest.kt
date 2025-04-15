package com.zen.recruitment.discount.strategy

import com.zen.recruitment.TestDataHelper.Companion.quantity
import com.zen.recruitment.discount.PercentageDiscountRetrieval
import com.zen.recruitment.discount.dto.Order
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal

class PercentageBasedDiscountStrategyTest {

    private val percentageDiscountRetrieval = mock<PercentageDiscountRetrieval>()

    private val uut = PercentageBasedDiscountStrategy(percentageDiscountRetrieval)

    @Test
    fun `apply should get discount value and apply it to the order`() {
        // Given
        val totalPrice = BigDecimal(100)
        val order = Order(totalPrice, quantity)
        val discountPercentage = BigDecimal(10)
        whenever(percentageDiscountRetrieval.getDiscountValue()).thenReturn(discountPercentage)

        // When
        val result = uut.apply(order)

        // Then
        val expectedValue = BigDecimal(90)
        assertEquals(expectedValue, result.totalPrice)
    }
}
