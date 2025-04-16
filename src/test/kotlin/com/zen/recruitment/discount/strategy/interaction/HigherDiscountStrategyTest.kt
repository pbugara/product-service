package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.dto.Order
import com.zen.recruitment.discount.strategy.policy.DiscountPolicyStrategy
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class HigherDiscountStrategyTest {

    private val discountPolicyStrategy1 = mock<DiscountPolicyStrategy>()
    private val discountPolicyStrategy2 = mock<DiscountPolicyStrategy>()
    private val uut = HigherDiscountStrategy(
            listOf(discountPolicyStrategy1, discountPolicyStrategy2)
    )

    @Test
    fun `apply should return order with the lowest total price (highest discount)`() {
        // Given
        val order = mock<Order>()
        val discountedOrder1 = mock<Order>()
        whenever(discountedOrder1.totalPrice).thenReturn(BigDecimal(100))
        val discountedOrder2 = mock<Order>()
        whenever(discountedOrder2.totalPrice).thenReturn(BigDecimal(50))

        whenever(discountPolicyStrategy1.apply(order)).thenReturn(discountedOrder1)
        whenever(discountPolicyStrategy2.apply(order)).thenReturn(discountedOrder2)

        // When
        val result = uut.apply(order)

        // Then
        assertEquals(discountedOrder2, result)
    }

    @Test
    fun `apply should return order calculated by first policy with the lowest total price (highest discount)`() {
        // Given
        val order = mock<Order>()
        val discountedOrder1 = mock<Order>()
        whenever(discountedOrder1.totalPrice).thenReturn(BigDecimal(50))
        val discountedOrder2 = mock<Order>()
        whenever(discountedOrder2.totalPrice).thenReturn(BigDecimal(100))

        whenever(discountPolicyStrategy1.apply(order)).thenReturn(discountedOrder1)
        whenever(discountPolicyStrategy2.apply(order)).thenReturn(discountedOrder2)

        // When
        val result = uut.apply(order)

        // Then
        assertEquals(discountedOrder1, result)
    }
}
