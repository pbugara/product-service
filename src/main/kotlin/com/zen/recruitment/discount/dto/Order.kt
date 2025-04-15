package com.zen.recruitment.discount.dto

import java.math.BigDecimal

data class Order(val totalPrice: BigDecimal, val quantity: Int) {

    companion object {
        fun copy(order: Order, newTotalPrice: BigDecimal) : Order {
            return Order(
                totalPrice = newTotalPrice,
                quantity = order.quantity
            )
        }
    }
}
