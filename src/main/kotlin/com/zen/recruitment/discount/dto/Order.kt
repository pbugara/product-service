package com.zen.recruitment.discount.dto

import java.math.BigDecimal

data class Order(val totalPrice: BigDecimal, val quantity: Int) : Comparable<Order> {

    companion object {
        fun copy(order: Order, newTotalPrice: BigDecimal) : Order {
            return Order(
                totalPrice = newTotalPrice,
                quantity = order.quantity
            )
        }
    }

    override fun compareTo(other: Order): Int {
        return this.totalPrice.compareTo(other.totalPrice)
    }
}
