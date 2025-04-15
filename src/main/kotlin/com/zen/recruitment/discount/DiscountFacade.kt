package com.zen.recruitment.discount

import com.zen.recruitment.discount.dto.Order
import com.zen.recruitment.discount.strategy.DiscountStrategyFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DiscountFacade(private val discountStrategyFactory: DiscountStrategyFactory) {

    fun calculateDiscountedPrice(productPrice: BigDecimal, quantity: Int) : BigDecimal {
        val totalPriceBeforeDiscounts = productPrice.multiply(quantity.toBigDecimal())
        val order = Order(totalPriceBeforeDiscounts, quantity)
        val discountStrategy = discountStrategyFactory.getActiveDiscountStrategy()
        val discountedOrder = discountStrategy.apply(order)
        return discountedOrder.totalPrice
    }
}
