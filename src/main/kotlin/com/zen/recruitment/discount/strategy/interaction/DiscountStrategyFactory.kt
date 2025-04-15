package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.DiscountConfigRepository
import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyType.CUMULATIVE
import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyType.HIGHER
import org.springframework.stereotype.Component

@Component
class DiscountStrategyFactory(
        private val discountConfigRepository: DiscountConfigRepository,
        private val higherDiscountStrategy: HigherDiscountStrategy,
        private val cumulativeDiscountStrategy: CumulativeDiscountStrategy,
        private val noDiscountStrategy: NoDiscountStrategy) {

    fun getActiveDiscountStrategy() : DiscountInteractionStrategy {
        return when (discountConfigRepository.findOneByActiveTrue()?.name) {
            HIGHER -> higherDiscountStrategy
            CUMULATIVE -> cumulativeDiscountStrategy
            else -> noDiscountStrategy
        }
    }
}
