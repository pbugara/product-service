package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.dto.Order
import org.springframework.stereotype.Component

@Component
class NoDiscountStrategy : DiscountStrategy {
    override fun apply(order: Order): Order {
        return order
    }
}
