package com.zen.recruitment.discount.strategy.policy

import com.zen.recruitment.TestDataHelper.Companion.quantity
import com.zen.recruitment.discount.QuantityDiscountRetrieval
import com.zen.recruitment.discount.dto.Order
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal

class QuantityBasedDiscountStrategyTest {

    private val quantityBasedDiscountStrategy = Mockito.mock<QuantityDiscountRetrieval>()

    private val uut = QuantityBasedDiscountStrategy(quantityBasedDiscountStrategy)

    @Test
    fun `apply should get discount value and apply it to the order`() {
        // Given
        val totalPrice = BigDecimal(100)
        val order = Order(totalPrice, quantity)
        val discountPercentage = BigDecimal(10)
        whenever(quantityBasedDiscountStrategy.getDiscountValue(quantity)).thenReturn(discountPercentage)

        // When
        val result = uut.apply(order)

        // Then
        val expectedValue = BigDecimal(90)
        Assertions.assertEquals(expectedValue, result.totalPrice)
    }
}
