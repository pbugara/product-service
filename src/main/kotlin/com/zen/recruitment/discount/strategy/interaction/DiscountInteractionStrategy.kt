package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.dto.Order

interface DiscountInteractionStrategy {
    fun apply(order: Order): Order
}
