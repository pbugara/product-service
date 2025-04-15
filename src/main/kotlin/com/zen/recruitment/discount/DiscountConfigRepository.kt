package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.DiscountConfig
import org.springframework.data.repository.CrudRepository

interface DiscountConfigRepository : CrudRepository<DiscountConfig, Long> {
    fun findOneByActiveTrue(): DiscountConfig?
}
