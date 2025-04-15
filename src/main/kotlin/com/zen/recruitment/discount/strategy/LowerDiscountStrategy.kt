package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.dto.Order
import org.springframework.stereotype.Component

@Component
class LowerDiscountStrategy(
        private val percentageBasedDiscountStrategy: PercentageBasedDiscountStrategy,
        private val quantityBasedDiscountStrategy: QuantityBasedDiscountStrategy
) : DiscountStrategy {
    override fun apply(order: Order): Order {
        return maxOf(
                percentageBasedDiscountStrategy.apply(order),
                quantityBasedDiscountStrategy.apply(order))
    }
}
