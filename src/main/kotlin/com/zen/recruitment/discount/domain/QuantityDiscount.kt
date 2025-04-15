package com.zen.recruitment.discount.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Entity
@DiscriminatorValue("QUANTITY")
class QuantityDiscount(
    @OneToMany(mappedBy = "discount", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val quantityConfigs: List<QuantityDiscountConfigs> = listOf()
) : Discount()
