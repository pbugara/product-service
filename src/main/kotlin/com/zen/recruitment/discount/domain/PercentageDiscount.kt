package com.zen.recruitment.discount.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("PERCENTAGE")
class PercentageDiscount(
    val percentage: Double
) : Discount()
