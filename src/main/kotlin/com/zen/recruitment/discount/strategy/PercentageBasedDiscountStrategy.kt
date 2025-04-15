package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.PercentageDiscountRetrieval
import com.zen.recruitment.discount.dto.Order
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PercentageBasedDiscountStrategy(private val percentageDiscountRetrieval: PercentageDiscountRetrieval) : DiscountStrategy {
    override fun apply(order: Order): Order {
        val discountValue = percentageDiscountRetrieval.getDiscountValue()
        val reversedValue = BigDecimal(100).subtract(discountValue)
        val discountedPrice = order.totalPrice.multiply(reversedValue).divide(BigDecimal(100))
        return Order.copy(order, discountedPrice)
    }
}
