package com.zen.recruitment.discount.strategy.policy

import com.zen.recruitment.discount.dto.Order

interface DiscountPolicyStrategy {
    fun apply(order: Order): Order
}
