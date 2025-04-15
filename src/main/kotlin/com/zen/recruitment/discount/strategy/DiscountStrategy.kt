package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.dto.Order

interface DiscountStrategy {
    fun apply(order: Order): Order
}
