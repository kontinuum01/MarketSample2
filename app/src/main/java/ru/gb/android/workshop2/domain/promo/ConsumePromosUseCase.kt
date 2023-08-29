package ru.gb.android.workshop2.domain.promo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.gb.android.workshop2.data.promo.PromoRepository

class ConsumePromosUseCase(
    private val promoRepository: PromoRepository,
    private val promoDomainMapper: PromoDomainMapper,
) {
    operator fun invoke(): Flow<List<Promo>> {
        return promoRepository.consumePromos()
            .onEach {
                // imitate some background work
                delay(1000)
            }
            .map{ promos -> promos.map(promoDomainMapper::fromEntity) }
    }
}