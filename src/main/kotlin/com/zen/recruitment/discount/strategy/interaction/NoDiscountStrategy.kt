package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.dto.Order
import org.springframework.stereotype.Component

@Component
class NoDiscountStrategy : DiscountInteractionStrategy {
    override fun apply(order: Order): Order {
        return order
    }
}
