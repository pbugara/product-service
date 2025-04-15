package com.zen.recruitment.discount.domain

import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType.SINGLE_TABLE

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
abstract class Discount(
        @Id
        @GeneratedValue
        val id: Long? = null
)
