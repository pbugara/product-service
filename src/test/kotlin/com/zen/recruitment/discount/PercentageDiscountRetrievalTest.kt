package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.PercentageDiscount
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal

class PercentageDiscountRetrievalTest {

    private val discountService = mock<DiscountService>()
    private val uut = PercentageDiscountRetrieval(discountService)

    @Test
    fun `getDiscountValue should return 0 when no discount found`() {
        // Given
        whenever(discountService.getPercentageDiscount()).thenReturn(null)

        // When
        val result = uut.getDiscountValue()

        // Then
        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun `getDiscountValue should return discount value when found`() {
        // Given
        val discount = 10
        val expectedDiscount = BigDecimal(discount)
        val percentageDiscount = PercentageDiscount(discount)
        whenever(discountService.getPercentageDiscount()).thenReturn(percentageDiscount)

        // When
        val result = uut.getDiscountValue()

        // Then
        assertEquals(expectedDiscount, result)
    }
}
