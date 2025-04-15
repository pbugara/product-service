package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.dto.Order
import com.zen.recruitment.discount.strategy.policy.DiscountPolicyStrategy
import org.springframework.stereotype.Component

@Component
class CumulativeDiscountStrategy(
        private val discountPolicies: List<DiscountPolicyStrategy>,
) : DiscountInteractionStrategy {
    override fun apply(order: Order): Order {
        var discountedOrder = order
        for (policy in discountPolicies) {
            discountedOrder = policy.apply(discountedOrder)
        }
        return discountedOrder
    }
}
