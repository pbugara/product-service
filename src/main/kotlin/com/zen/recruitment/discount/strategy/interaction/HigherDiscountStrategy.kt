package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.dto.Order
import com.zen.recruitment.discount.strategy.policy.DiscountPolicyStrategy
import org.springframework.stereotype.Component

@Component
class HigherDiscountStrategy(
        private val discountPolicies: List<DiscountPolicyStrategy>
) : DiscountInteractionStrategy {
    override fun apply(order: Order): Order {
        return discountPolicies.map { it.apply(order) }
                .minBy { it.totalPrice }
    }
}
