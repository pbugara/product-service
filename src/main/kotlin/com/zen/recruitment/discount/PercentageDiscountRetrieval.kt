package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.PercentageDiscount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

// todo: rename
// todo: java docs
@Component
class PercentageDiscountRetrieval(private val discountService: DiscountService) {

    private val log: Logger = LoggerFactory.getLogger(PercentageDiscountRetrieval::class.java)

    fun getDiscountValue(): BigDecimal {
        val discounts = discountService.getDiscountsByType(DiscountType.PERCENTAGE)
        if (discounts.isEmpty()) {
            log.warn("No percentage discounts found in the database. Returning 0.")
            return BigDecimal.ZERO
        } else if (discounts.size > 1) {
            log.warn("Multiple percentage discounts found in the database. Returning 0.")
            return BigDecimal.ZERO
        }
        val percentageDiscount = discounts[0] as PercentageDiscount
        return BigDecimal(percentageDiscount.percentage)
    }
    // todo - store bigdecimal in db
    // todo - refactor
    // todo - valiadte if there is no overlap in the configs
}
