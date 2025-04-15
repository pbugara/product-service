package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.Discount
import com.zen.recruitment.discount.domain.QuantityDiscount
import com.zen.recruitment.discount.domain.QuantityDiscountConfigs
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

// todo: java docs
@Component
class QuantityDiscountRetrieval(private val discountService: DiscountService) {

    private val log: Logger = LoggerFactory.getLogger(QuantityDiscountRetrieval::class.java)

    fun getDiscountValue(quantity: Int): BigDecimal {
        val discounts = discountService.getDiscountsByType(DiscountType.QUANTITY)
        if (!isDiscountValid(discounts)) {
            return BigDecimal.ZERO
        }
        val quantityDiscount = discounts[0] as QuantityDiscount
        val applicableConfig = getApplicableConfig(quantityDiscount, quantity)
        return applicableConfig?.percentage?.toBigDecimal() ?: BigDecimal.ZERO
    }

    fun isDiscountValid(discounts: List<Discount>): Boolean {
        if (discounts.isEmpty() || discounts.size > 1) {
            log.warn("Invalid discount configuration")
            return false
        }
        return true
    }

    fun getApplicableConfig(quantityDiscount: QuantityDiscount, quantity: Int): QuantityDiscountConfigs? {
        val allMatchingConfigs = quantityDiscount.quantityConfigs.filter { it.minQty <= quantity && it.maxQty >= quantity }
        return allMatchingConfigs.singleOrNull()
    }
}
