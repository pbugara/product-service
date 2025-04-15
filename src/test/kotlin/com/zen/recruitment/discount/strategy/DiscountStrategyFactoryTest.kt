package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.DiscountConfigRepository
import com.zen.recruitment.discount.domain.DiscountConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DiscountStrategyFactoryTest {

    private val discountConfigRepository = mock<DiscountConfigRepository>()
    private val percentageBasedDiscountStrategy = mock<PercentageBasedDiscountStrategy>()
    private val quantityBasedDiscountStrategy = mock<QuantityBasedDiscountStrategy>()
    private val higherDiscountStrategy = mock<HigherDiscountStrategy>()
    private val lowerDiscountStrategy = mock<LowerDiscountStrategy>()
    private val percentageThenQuantityDiscountStrategy = mock<PercentageThenQuantityDiscountStrategy>()
    private val quantityThenPercentageDiscountStrategy = mock<QuantityThenPercentageDiscountStrategy>()
    private val noDiscountStrategy = mock<NoDiscountStrategy>()

    private val uut = DiscountStrategyFactory(
        discountConfigRepository,
        percentageBasedDiscountStrategy,
        quantityBasedDiscountStrategy,
        higherDiscountStrategy,
        lowerDiscountStrategy,
        percentageThenQuantityDiscountStrategy,
        quantityThenPercentageDiscountStrategy,
        noDiscountStrategy
    )

    private fun strategyMapping() = listOf(
            Arguments.of(DiscountStrategyType.PERCENTAGE, percentageBasedDiscountStrategy),
            Arguments.of(DiscountStrategyType.QUANTITY, quantityBasedDiscountStrategy),
            Arguments.of(DiscountStrategyType.HIGHER, higherDiscountStrategy),
            Arguments.of(DiscountStrategyType.LOWER, lowerDiscountStrategy),
            Arguments.of(DiscountStrategyType.PERCENTAGE_THEN_QUANTITY, percentageThenQuantityDiscountStrategy),
            Arguments.of(DiscountStrategyType.QUANTITY_THEN_PERCENTAGE, quantityThenPercentageDiscountStrategy),
            Arguments.of(DiscountStrategyType.NO_DISCOUNT, noDiscountStrategy)
    )

    @ParameterizedTest
    @MethodSource("strategyMapping")
    fun `getActiveDiscountStrategy should return the correct discount strategy based on the active discount config`(discountStrategyType: DiscountStrategyType,
                                                                                                                discountStrategy: DiscountStrategy) {
        // Given
        val activeDiscountConfig = mock<DiscountConfig>()
        whenever(activeDiscountConfig.name).thenReturn(discountStrategyType)
        whenever(discountConfigRepository.findOneByActiveTrue()).thenReturn(activeDiscountConfig)

        // When
        val response = uut.getActiveDiscountStrategy()

        // Then
        assertEquals(discountStrategy, response)
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
