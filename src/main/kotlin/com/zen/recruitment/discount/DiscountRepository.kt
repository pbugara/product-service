package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.Discount
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository

interface DiscountRepository : CrudRepository<Discount, Long> {
    @EntityGraph(attributePaths = ["quantityConfigs"])
    fun findByType(type: String): List<Discount>
}
