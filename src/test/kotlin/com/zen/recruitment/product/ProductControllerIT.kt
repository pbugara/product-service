package com.zen.recruitment.product

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    companion object {
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16")
                .withDatabaseName("mydatabase")
                .withUsername("myuser")
                .withPassword("secret")
                .withInitScript("init.sql") // umieść w src/test/resources/db/init.sql
                .apply { start() }

        @JvmStatic
        @DynamicPropertySource
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }

        @JvmStatic
        @AfterAll
        fun stopContainer() {
            postgres.stop()
        }
    }

    @AfterEach
    fun resetDatabase() {
        // Reset the database to its initial state after each test
        jdbcTemplate.execute("UPDATE percentage_discount SET percentage = 10 where id =1")
        jdbcTemplate.execute("UPDATE discount_interaction set active = false where name = 'CUMULATIVE'")
        jdbcTemplate.execute("UPDATE discount_interaction set active = true where name = 'HIGHER'")
    }

    @Test
    fun `should return product by id`() {
        val expectedId = "550e8400-e29b-41d4-a716-446655440000"

        mockMvc.perform(get("/api/v1/products/$expectedId"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(expectedId))
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.description").value("Description for Product 1"))
                .andExpect(jsonPath("$.price").value(19.99))
    }

    @Test
    fun `should return 404 when product not found`() {
        val nonExistentId = "550e8400-e29b-41d4-a716-446655440001"

        mockMvc.perform(get("/api/v1/products/$nonExistentId"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.detail").value("Product with id $nonExistentId not found"))
    }

    @Test
    fun `should return 400 when id is in invalid type`() {
        val invalidId = "invalid-id"

        mockMvc.perform(get("/api/v1/products/$invalidId"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.detail").value("Invalid method argument type"))
    }

    // default db init -> src/test/resources/db/init.sql
    // higher discount will be applied
    // 10% for percentage, 0% for quantity
    @Test
    fun `should return product price with higher discount - percentage`() {
        val expectedId = "550e8400-e29b-41d4-a716-446655440000"
        val quantity = 5
        val expectedDiscountedPrice = 89.96 // 10% discount applied - 19.99 * 5 * 0.9

        mockMvc.perform(get("/api/v1/products/$expectedId/quantity/$quantity/price"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.productId").value(expectedId))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.totalPrice").value(expectedDiscountedPrice))
    }

    // default db init -> src/test/resources/db/init.sql
    // higher discount will be applied
    // 10% for percentage, 15% for quantity
    @Test
    fun `should return product price with higher discount - quantity`() {
        val expectedId = "550e8400-e29b-41d4-a716-446655440000"
        val quantity = 50
        val expectedDiscountedPrice = 849.58 // 15% discount applied - 19.99 * 50 * 0.85

        mockMvc.perform(get("/api/v1/products/$expectedId/quantity/$quantity/price"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.productId").value(expectedId))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.totalPrice").value(expectedDiscountedPrice))
    }

    // changes in db config
    // cumulative discount will be applied
    // 10% for percentage, 15% for quantity
    @Test
    fun `should return product price with cumulative discount`() {
        // disable higher discount
        jdbcTemplate.update(
                "UPDATE discount_interaction SET active = false WHERE name = 'HIGHER'",
        )
        // enable cumulative discount
        jdbcTemplate.update(
                "UPDATE discount_interaction SET active = true WHERE name = 'CUMULATIVE'",
        )
        val expectedId = "550e8400-e29b-41d4-a716-446655440000"
        val quantity = 50
        val expectedDiscountedPrice = 764.62 // 15% discount applied and then 10% discount applied - 19.99 * 50 * 0.85 * 0.9

        mockMvc.perform(get("/api/v1/products/$expectedId/quantity/$quantity/price"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.productId").value(expectedId))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.totalPrice").value(expectedDiscountedPrice))
    }

    // changes in db config
    // no discount will be applied
    @Test
    fun `should return product price without discount`() {
        // disable higher discount
        jdbcTemplate.update(
                "UPDATE discount_interaction SET active = false WHERE name = 'HIGHER'",
        )
        // disable cumulative discount
        jdbcTemplate.update(
                "UPDATE discount_interaction SET active = false WHERE name = 'CUMULATIVE'",
        )
        val expectedId = "550e8400-e29b-41d4-a716-446655440000"
        val quantity = 50
        val expectedDiscountedPrice = 999.5 // no discount applied - 19.99 * 50

        mockMvc.perform(get("/api/v1/products/$expectedId/quantity/$quantity/price"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.productId").value(expectedId))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.totalPrice").value(expectedDiscountedPrice))
    }

    // changes in db config
    // discount will be updated after first request
    @Test
    fun `should return product price for original discount and then for updated one`() {
        val expectedId = "550e8400-e29b-41d4-a716-446655440000"
        val quantity = 5
        val expectedDiscountedPrice = 89.96 // 10% discount applied - 19.99 * 5 * 0.9

        mockMvc.perform(get("/api/v1/products/$expectedId/quantity/$quantity/price"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.productId").value(expectedId))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.totalPrice").value(expectedDiscountedPrice))

        // change discount to 20%
        jdbcTemplate.update(
                "UPDATE percentage_discount SET percentage = 20 WHERE id = 1",
        )

        val expectedPriceDiscountedBy20Percent = 79.96 // 20% discount applied - 19.99 * 5 * 0.8
        mockMvc.perform(get("/api/v1/products/$expectedId/quantity/$quantity/price"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.productId").value(expectedId))
                .andExpect(jsonPath("$.quantity").value(quantity))
                .andExpect(jsonPath("$.totalPrice").value(expectedPriceDiscountedBy20Percent))
    }
}
