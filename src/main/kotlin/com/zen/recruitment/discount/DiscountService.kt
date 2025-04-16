package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.PercentageDiscount
import com.zen.recruitment.discount.domain.QuantityDiscount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DiscountService (
        private val quantityDiscountRepository: QuantityDiscountRepository,
        private val percentageDiscountRepository: PercentageDiscountRepository
) {

    private val log: Logger = LoggerFactory.getLogger(DiscountService::class.java)

    /**
     * Retrieves the percentage discount from the database.
     * If no discount is found, it returns null.
     * @return PercentageDiscount object or null if not found.
     */
    fun getPercentageDiscount(): PercentageDiscount? {
        val discounts = percentageDiscountRepository.findAll()
        if (discounts.isEmpty()) {
            log.warn("Discounts of type ${DiscountType.PERCENTAGE.name} not found in the database. Returning empty list.")
            return null
        }
        return discounts[0]
    }

    /**
     * Retrieves the quantity discount from the database.
     * If no discount is found, it returns null.
     * @return QuantityDiscount object or null if not found.
     */
    fun getQuantityDiscount(): QuantityDiscount? {
        val discounts = quantityDiscountRepository.findAll()
        if (discounts.isEmpty()) {
            log.warn("Discounts of type ${DiscountType.QUANTITY.name} not found in the database. Returning empty list.")
            return null
        }
        return discounts[0]
    }
}
