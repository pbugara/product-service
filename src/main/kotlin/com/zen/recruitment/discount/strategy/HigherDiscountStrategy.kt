package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.dto.Order
import org.springframework.stereotype.Component

@Component
class HigherDiscountStrategy(
        private val percentageBasedDiscountStrategy: PercentageBasedDiscountStrategy,
        private val quantityBasedDiscountStrategy: QuantityBasedDiscountStrategy
) : DiscountStrategy {
    override fun apply(order: Order): Order {
        return minOf(
                percentageBasedDiscountStrategy.apply(order),
                quantityBasedDiscountStrategy.apply(order))
    }
}
