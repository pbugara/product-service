package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.dto.Order
import org.springframework.stereotype.Component

@Component
class PercentageThenQuantityDiscountStrategy(
        private val percentageBasedDiscountStrategy: PercentageBasedDiscountStrategy,
        private val quantityBasedDiscountStrategy: QuantityBasedDiscountStrategy
) : DiscountStrategy {
    override fun apply(order: Order): Order {
        var orderAfterPercentageDiscount = percentageBasedDiscountStrategy.apply(order)
        return quantityBasedDiscountStrategy.apply(orderAfterPercentageDiscount)
    }
}
