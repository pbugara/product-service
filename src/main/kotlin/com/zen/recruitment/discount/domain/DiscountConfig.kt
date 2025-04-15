package com.zen.recruitment.discount.domain

import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "discount_config")
data class DiscountConfig(
        @Id
        val id: UUID = UUID.randomUUID(),

        @Enumerated(EnumType.STRING)
        val name: DiscountStrategyType,

        val active: Boolean
)
