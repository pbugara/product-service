package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.DiscountConfigRepository
import com.zen.recruitment.discount.domain.DiscountConfig
import com.zen.recruitment.discount.strategy.interaction.CumulativeDiscountStrategy
import com.zen.recruitment.discount.strategy.interaction.DiscountInteractionStrategy
import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyFactory
import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyType
import com.zen.recruitment.discount.strategy.interaction.HigherDiscountStrategy
import com.zen.recruitment.discount.strategy.interaction.NoDiscountStrategy
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

    private val discountConfigRepository = mock<DiscountConfigRepository>()
    private val higherDiscountStrategy = mock<HigherDiscountStrategy>()
    private val cumulativeDiscountStrategy = mock<CumulativeDiscountStrategy>()
    private val noDiscountStrategy = mock<NoDiscountStrategy>()

    private val uut = DiscountStrategyFactory(
        discountConfigRepository,
        higherDiscountStrategy,
        cumulativeDiscountStrategy,
        noDiscountStrategy
    )

    private fun strategyMapping() = listOf(
            Arguments.of(DiscountStrategyType.HIGHER, higherDiscountStrategy),
            Arguments.of(DiscountStrategyType.CUMULATIVE, cumulativeDiscountStrategy),
            Arguments.of(DiscountStrategyType.NO_DISCOUNT, noDiscountStrategy)
    )

    @ParameterizedTest
    @MethodSource("strategyMapping")
    fun `getActiveDiscountStrategy should return the correct discount strategy based on the active discount config`(discountStrategyType: DiscountStrategyType,
                                                                                                                    discountInteractionStrategy: DiscountInteractionStrategy) {
        // Given
        val activeDiscountConfig = mock<DiscountConfig>()
        whenever(activeDiscountConfig.name).thenReturn(discountStrategyType)
        whenever(discountConfigRepository.findOneByActiveTrue()).thenReturn(activeDiscountConfig)

        // When
        val response = uut.getActiveDiscountStrategy()

        // Then
        assertEquals(discountInteractionStrategy, response)
    }

    @Test
    fun `getActiveDiscountStrategy should return no discount strategy when no active discount config is found`() {
        // Given
        whenever(discountConfigRepository.findOneByActiveTrue()).thenReturn(null)

        // When
        val response = uut.getActiveDiscountStrategy()

        // Then
        assertEquals(noDiscountStrategy, response)
    }
}
