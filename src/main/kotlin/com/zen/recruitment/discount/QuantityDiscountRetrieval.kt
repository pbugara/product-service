package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.QuantityDiscount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

// todo: rename
// todo: java docs
@Component
class QuantityDiscountRetrieval(private val discountService: DiscountService) {

    private val log: Logger = LoggerFactory.getLogger(QuantityDiscountRetrieval::class.java)

    fun getDiscountValue(quantity: Int): BigDecimal {
        val discounts = discountService.getDiscountsByType(DiscountType.QUANTITY)
        if (discounts.isEmpty()) {
            log.warn("No quantity discounts found in the database. Returning 0.")
            return BigDecimal.ZERO
        } else if (discounts.size > 1) {
            log.warn("Multiple quantity discounts found in the database. Returning 0.")
            return BigDecimal.ZERO
        }
        val quantityDiscount = discounts[0] as QuantityDiscount
        val configs = quantityDiscount.quantityConfigs
        val applicableConfig = configs.find { it.minQty <= quantity && it.maxQty >= quantity }
        return if (applicableConfig != null) {
            return BigDecimal(applicableConfig.percentage)
        } else {
            log.info("No applicable quantity discount found. Returning 0.")
            return BigDecimal.ZERO
        }
    }
    // todo - store bigdecimal in db
    // todo - refactor
    // todo - valiadte if there is no overlap in the configs
}
