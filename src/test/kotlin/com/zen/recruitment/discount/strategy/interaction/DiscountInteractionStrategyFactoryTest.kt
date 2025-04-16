package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.DiscountInteractionRepository
import com.zen.recruitment.discount.domain.DiscountInteraction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DiscountInteractionStrategyFactoryTest {

    private val discountInteractionRepository = mock<DiscountInteractionRepository>()
    private val higherDiscountStrategy = mock<HigherDiscountStrategy>()
    private val cumulativeDiscountStrategy = mock<CumulativeDiscountStrategy>()
    private val noDiscountStrategy = mock<NoDiscountStrategy>()

    private val uut = DiscountStrategyFactory(
        discountInteractionRepository,
        higherDiscountStrategy,
        cumulativeDiscountStrategy,
        noDiscountStrategy
    )

    private fun strategyMapping() = listOf(
            Arguments.of(DiscountStrategyType.HIGHER, higherDiscountStrategy),
            Arguments.of(DiscountStrategyType.CUMULATIVE, cumulativeDiscountStrategy),
    )

    @ParameterizedTest
    @MethodSource("strategyMapping")
    fun `getActiveDiscountStrategy should return the correct discount strategy based on the active discount config`(discountStrategyType: DiscountStrategyType,
                                                                                                                    discountInteractionStrategy: DiscountInteractionStrategy) {
        // Given
        val activeDiscountInteraction = mock<DiscountInteraction>()
        whenever(activeDiscountInteraction.name).thenReturn(discountStrategyType)
        whenever(discountInteractionRepository.findOneByActiveTrue()).thenReturn(activeDiscountInteraction)

        // When
        val response = uut.getActiveDiscountStrategy()

        // Then
        assertEquals(discountInteractionStrategy, response)
    }

    @Test
    fun `getActiveDiscountStrategy should return no discount strategy when no active discount config is found`() {
        // Given
        whenever(discountInteractionRepository.findOneByActiveTrue()).thenReturn(null)

        // When
        val response = uut.getActiveDiscountStrategy()

        // Then
        assertEquals(noDiscountStrategy, response)
    }
}
