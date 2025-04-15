package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.dto.Order
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class NoDiscountStrategyTest {

    @Test
    fun `apply should return the same order`() {
        // Given
        val order = Order(BigDecimal(100), 2)
        val noDiscountStrategy = NoDiscountStrategy()

        // When
        val result = noDiscountStrategy.apply(order)

        // Then
        assertEquals(order, result)
    }
}
