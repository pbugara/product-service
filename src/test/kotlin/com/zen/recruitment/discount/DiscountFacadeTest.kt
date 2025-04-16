package com.zen.recruitment.discount

import com.zen.recruitment.TestDataHelper.Companion.discountedPrice
import com.zen.recruitment.TestDataHelper.Companion.productPrice
import com.zen.recruitment.TestDataHelper.Companion.quantity
import com.zen.recruitment.TestDataHelper.Companion.totalPrice
import com.zen.recruitment.discount.dto.Order
import com.zen.recruitment.discount.strategy.interaction.DiscountInteractionStrategy
import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class DiscountFacadeTest {

    private val discountStrategyFactory = mock<DiscountStrategyFactory>()

    private val discountInteractionStrategy = mock<DiscountInteractionStrategy>()

    private val uut: DiscountFacade = DiscountFacade(discountStrategyFactory)

    @Test
    fun `calculateDiscountedPrice should return discounted price`() {
        // Given
        val productPrice = productPrice
        val quantity = quantity
        val order = Order(totalPrice, quantity)
        val discountedOrder = Order(discountedPrice, quantity)
        whenever(discountStrategyFactory.getActiveDiscountStrategy()).thenReturn(discountInteractionStrategy)
        whenever(discountInteractionStrategy.apply(order)).thenReturn(discountedOrder)
        // When
        val response = uut.calculateDiscountedPrice(productPrice, quantity)

        // Then
        assertEquals(discountedPrice, response)
    }
}
