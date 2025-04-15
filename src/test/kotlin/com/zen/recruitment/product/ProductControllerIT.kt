package com.zen.recruitment.product

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
}
