package com.zen.recruitment.discount

import com.zen.recruitment.discount.domain.QuantityDiscount
import org.springframework.data.jpa.repository.JpaRepository

interface QuantityDiscountRepository : JpaRepository<QuantityDiscount, Long>
