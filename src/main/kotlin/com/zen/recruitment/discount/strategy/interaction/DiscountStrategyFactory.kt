package com.zen.recruitment.discount.strategy.interaction

import com.zen.recruitment.discount.DiscountInteractionRepository
import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyType.CUMULATIVE
import com.zen.recruitment.discount.strategy.interaction.DiscountStrategyType.HIGHER
import org.springframework.stereotype.Component

@Component
class DiscountStrategyFactory(
        private val discountInteractionRepository: DiscountInteractionRepository,
        private val higherDiscountStrategy: HigherDiscountStrategy,
        private val cumulativeDiscountStrategy: CumulativeDiscountStrategy,
        private val noDiscountStrategy: NoDiscountStrategy) {

    fun getActiveDiscountStrategy() : DiscountInteractionStrategy {
        return when (discountInteractionRepository.findOneByActiveTrue()?.name) {
            HIGHER -> higherDiscountStrategy
            CUMULATIVE -> cumulativeDiscountStrategy
            else -> noDiscountStrategy
        }
    }
}
