package com.zen.recruitment.product

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "products")
data class Product(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val price: BigDecimal
)
