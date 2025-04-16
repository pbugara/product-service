package com.zen.recruitment.product

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductRepository : CrudRepository<Product, UUID> {
    fun findPriceById(id: UUID): ProductPriceProjection?
}
