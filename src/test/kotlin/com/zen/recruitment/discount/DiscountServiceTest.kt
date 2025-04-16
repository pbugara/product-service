package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.PercentageDiscount
import com.zen.recruitment.discount.domain.QuantityDiscount
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DiscountServiceTest {

    private val quantityDiscountRepository = mock<QuantityDiscountRepository>()
    private val percentageDiscountRepository = mock<PercentageDiscountRepository>()
    private val uut = DiscountService(quantityDiscountRepository, percentageDiscountRepository)

    @Test
    fun `getPercentageDiscount should return null when no discounts found`() {
        // Given
        whenever(percentageDiscountRepository.findAll()).thenReturn(emptyList())

        // When
        val result = uut.getPercentageDiscount()

        // Then
        assertNull(result)
    }

    @Test
    fun `getQuantityDiscount should return null when no discounts found`() {
        // Given
        whenever(quantityDiscountRepository.findAll()).thenReturn(emptyList())

        // When
        val result = uut.getQuantityDiscount()

        // Then
        assertNull(result)
    }

    @Test
    fun `getPercentageDiscount should return first discount when found`() {
        // Given
        val expectedDiscount = PercentageDiscount(10)
        whenever(percentageDiscountRepository.findAll()).thenReturn(listOf(expectedDiscount))

        // When
        val result = uut.getPercentageDiscount()

        // Then
        assertEquals(expectedDiscount, result)
    }

    @Test
    fun `getQuantityDiscount should return first discount when found`() {
        // Given
        val expectedDiscount = QuantityDiscount()
        whenever(quantityDiscountRepository.findAll()).thenReturn(listOf(expectedDiscount))

        // When
        val result = uut.getQuantityDiscount()

        // Then
        assertEquals(expectedDiscount, result)
    }
}
