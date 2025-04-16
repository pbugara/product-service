package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.PercentageDiscount
import org.springframework.data.jpa.repository.JpaRepository

interface PercentageDiscountRepository : JpaRepository<PercentageDiscount, Long>
