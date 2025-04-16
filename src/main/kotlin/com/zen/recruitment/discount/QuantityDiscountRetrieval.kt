package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.QuantityDiscount
import com.zen.recruitment.discount.domain.QuantityDiscountConfigs
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class QuantityDiscountRetrieval(private val discountService: DiscountService) {

    /**
     * Retrieves the percentage discount value based on the quantity.
     * If no discount is found, it returns 0.
     * @param quantity The quantity for which to retrieve the discount value.
     * @return The percentage discount value as a BigDecimal.
     */
    fun getDiscountValue(quantity: Int): BigDecimal {
        val discount = discountService.getQuantityDiscount()
        val applicableConfig = getApplicableConfig(discount, quantity)
        return applicableConfig?.percentage?.toBigDecimal() ?: BigDecimal.ZERO
    }

    private fun getApplicableConfig(quantityDiscount: QuantityDiscount?, quantity: Int): QuantityDiscountConfigs? {
        val allMatchingConfigs = quantityDiscount?.quantityConfigs?.filter { it.minQty <= quantity && (it.maxQty == null || it.maxQty!! >= quantity) }
        return allMatchingConfigs?.singleOrNull()
    }
}
