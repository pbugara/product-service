package com.zen.recruitment.discount

import com.zen.recruitment.discount.dto.Order
import com.zen.recruitment.discount.strategy.DiscountStrategyFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DiscountFacade(private val discountStrategyFactory: DiscountStrategyFactory) {

    /**
     * Calculates the discounted price based on the product price and quantity.
     * It applies active discount strategy.
     *
     * @param productPrice The price of the product.
     * @param quantity The quantity of the product.
     * @return The discounted total price.
     */
    fun calculateDiscountedPrice(productPrice: BigDecimal, quantity: Int) : BigDecimal {
        val totalPriceBeforeDiscounts = productPrice.multiply(quantity.toBigDecimal())
        val order = Order(totalPriceBeforeDiscounts, quantity)
        val discountStrategy = discountStrategyFactory.getActiveDiscountStrategy()
        val discountedOrder = discountStrategy.apply(order)
        return discountedOrder.totalPrice
    }
}
