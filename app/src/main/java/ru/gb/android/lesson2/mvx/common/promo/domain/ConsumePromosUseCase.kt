package ru.gb.android.lesson2.mvx.common.promo.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gb.android.lesson2.mvx.common.promo.data.PromoRepository

class ConsumePromosUseCase(
    private val promoRepository: PromoRepository,
    private val promoDomainMapper: PromoDomainMapper,
) {
    operator fun invoke(): Flow<List<Promo>> {
        return promoRepository.consumePromos()
            .map{ promos -> promos.map(promoDomainMapper::fromEntity) }
    }
}