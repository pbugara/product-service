package com.zen.recruitment.discount.domain

import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "discount_interaction")
data class DiscountInteraction(
        @Id
        @GeneratedValue
        val id: Long,

        @Enumerated(EnumType.STRING)
        val name: DiscountStrategyType,

        val active: Boolean
)
