package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.Discount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DiscountService (private val discountRepository: DiscountRepository) {

    private val log: Logger = LoggerFactory.getLogger(DiscountService::class.java)

    fun getDiscountsByType(type: DiscountType): List<Discount> {
        val discounts = discountRepository.findByType(type.name)
        if (discounts.isEmpty()) {
            log.warn("Discounts of type ${type.name} not found in the database. Returning empty list.")
        }
        return discounts
    }
}
