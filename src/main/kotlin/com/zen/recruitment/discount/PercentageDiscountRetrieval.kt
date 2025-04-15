package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.Discount
import com.zen.recruitment.discount.domain.PercentageDiscount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

// todo: java docs
@Component
class PercentageDiscountRetrieval(private val discountService: DiscountService) {

    private val log: Logger = LoggerFactory.getLogger(PercentageDiscountRetrieval::class.java)

    fun getDiscountValue(): BigDecimal {
        val discounts = discountService.getDiscountsByType(DiscountType.PERCENTAGE)
        if (!isDiscountValid(discounts)) {
            return BigDecimal.ZERO
        }
        val percentageDiscount = discounts[0] as PercentageDiscount
        return BigDecimal(percentageDiscount.percentage)
    }

    fun isDiscountValid(discounts: List<Discount>): Boolean {
        if (discounts.isEmpty() || discounts.size > 1) {
            log.warn("Invalid discount configuration")
            return false
        }
        val discount = discounts[0] as PercentageDiscount
        val percentage = discount.percentage
        return percentage in (1..100)
    }
}
