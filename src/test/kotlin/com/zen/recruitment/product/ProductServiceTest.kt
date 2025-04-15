package com.zen.recruitment.product

import com.zen.recruitment.TestDataHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.*

class ProductServiceTest {

    val productRepository = mock<ProductRepository>()

    val uut = ProductService(productRepository)

    @Test
    fun `getProductById should return product`() {
        // Given
         val productId = UUID.randomUUID()
         val expectedProduct = TestDataHelper.getProduct()

         whenever(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct))

         // When
         val response = uut.getProductById(productId)

         // Then
         assertEquals(expectedProduct, response)
    }

    @Test
    fun `getProductById should throw ProductException when product not found`() {
        // Given
        val productId = UUID.randomUUID()

        whenever(productRepository.findById(productId)).thenReturn(Optional.empty())

        // When / Then
        val exception = assertThrows<ProductException> {
            uut.getProductById(productId)
        }
        assertEquals("Product with id $productId not found", exception.message)
    }
}
