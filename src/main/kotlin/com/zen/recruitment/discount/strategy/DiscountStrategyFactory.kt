package com.zen.recruitment.discount.strategy

import com.zen.recruitment.discount.DiscountConfigRepository
import com.zen.recruitment.discount.strategy.DiscountStrategyType.HIGHER
import com.zen.recruitment.discount.strategy.DiscountStrategyType.LOWER
import com.zen.recruitment.discount.strategy.DiscountStrategyType.PERCENTAGE
import com.zen.recruitment.discount.strategy.DiscountStrategyType.PERCENTAGE_THEN_QUANTITY
import com.zen.recruitment.discount.strategy.DiscountStrategyType.QUANTITY
import com.zen.recruitment.discount.strategy.DiscountStrategyType.QUANTITY_THEN_PERCENTAGE
import org.springframework.stereotype.Component

@Component
class DiscountStrategyFactory(
        private val discountConfigRepository: DiscountConfigRepository,
        private val percentageBasedDiscountStrategy: PercentageBasedDiscountStrategy,
        private val quantityBasedDiscountStrategy: QuantityBasedDiscountStrategy,
        private val higherDiscountStrategy: HigherDiscountStrategy,
        private val lowerDiscountStrategy: LowerDiscountStrategy,
        private val percentageThenQuantityDiscountStrategy: PercentageThenQuantityDiscountStrategy,
        private val quantityThenPercentageDiscountStrategy: QuantityThenPercentageDiscountStrategy,
        private val noDiscountStrategy: NoDiscountStrategy) {

    fun getActiveDiscountStrategy() : DiscountStrategy {
        return when (discountConfigRepository.findOneByActiveTrue()?.name) {
            PERCENTAGE -> percentageBasedDiscountStrategy
            QUANTITY -> quantityBasedDiscountStrategy
            HIGHER -> higherDiscountStrategy
            LOWER -> lowerDiscountStrategy
            PERCENTAGE_THEN_QUANTITY -> percentageThenQuantityDiscountStrategy
            QUANTITY_THEN_PERCENTAGE -> quantityThenPercentageDiscountStrategy
            else -> noDiscountStrategy
        }
    }
}
