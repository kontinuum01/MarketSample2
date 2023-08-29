package ru.gb.android.workshop2.presentation.card.start

import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {

    fun providePresenter(): ProductPresenter {
        return ProductPresenter(
            consumeFirstProductUseCase = ServiceLocator.provideConsumeFirstProductUseCase(),
            productVOFactory = provideProductVOFactory(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
        )
    }

    private fun provideProductVOFactory(): ProductVOFactory {
        return ProductVOFactory(
            discountFormatter = ServiceLocator.provideDiscountFormatter(),
            priceFormatter = ServiceLocator.providePriceFormatter(),
        )
    }
}