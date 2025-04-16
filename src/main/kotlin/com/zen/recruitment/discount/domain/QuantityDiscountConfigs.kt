package com.zen.recruitment.discount.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class QuantityDiscountConfigs(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val minQty: Int,
        val maxQty: Int?,
        val percentage: Int,
        @ManyToOne
        @JoinColumn(name = "discount_id")
        val discount: QuantityDiscount
)
