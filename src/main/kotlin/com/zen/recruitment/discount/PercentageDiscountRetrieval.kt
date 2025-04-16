package com.zen.recruitment.discount

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PercentageDiscountRetrieval(private val discountService: DiscountService) {

    /**
     * Retrieves the percentage discount value from the discount service.
     * If no discount is found, it returns 0.
     * @return The percentage discount value as a BigDecimal.
     */
    fun getDiscountValue(): BigDecimal {
        val discount = discountService.getPercentageDiscount()
        return discount?.percentage?.toBigDecimal() ?: BigDecimal.ZERO
    }
}
