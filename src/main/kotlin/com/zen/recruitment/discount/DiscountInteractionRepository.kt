package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.DiscountInteraction
import org.springframework.data.repository.CrudRepository

interface DiscountInteractionRepository : CrudRepository<DiscountInteraction, Long> {
    fun findOneByActiveTrue(): DiscountInteraction?
}
