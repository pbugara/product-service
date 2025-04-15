package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.dto.Order
import com.zen.recruitment.discount.strategy.policy.DiscountPolicyStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CumulativeDiscountStrategyTest {

    private val discountPolicyStrategy1 = mock<DiscountPolicyStrategy>()
    private val discountPolicyStrategy2 = mock<DiscountPolicyStrategy>()
    private val uut = CumulativeDiscountStrategy(
        listOf(discountPolicyStrategy1, discountPolicyStrategy2)
    )

    @Test
    fun `apply should apply all discount policies in order`() {
        // Given
        val order = mock<Order>()
        val discountedOrder1 = mock<Order>()
        val discountedOrder2 = mock<Order>()

        whenever(discountPolicyStrategy1.apply(order)).thenReturn(discountedOrder1)
        whenever(discountPolicyStrategy2.apply(discountedOrder1)).thenReturn(discountedOrder2)

        // When
        val result = uut.apply(order)

        // Then
        assertEquals(discountedOrder2, result)
        verify(discountPolicyStrategy1).apply(order)
        verify(discountPolicyStrategy2).apply(discountedOrder1)
    }
}
