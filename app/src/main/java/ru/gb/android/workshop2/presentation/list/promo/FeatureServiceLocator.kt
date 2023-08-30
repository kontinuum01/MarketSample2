package ru.gb.android.workshop2.presentation.list.promo

import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {

    fun providePresenter(): PromoListPresenter {
        return PromoListPresenter(
            promoVOMapper = provideProductVOMapper(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
        )
    }

    private fun provideProductVOMapper(): PromoVOMapper {
        return PromoVOMapper()
    }
}