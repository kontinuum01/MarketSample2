package ru.gb.android.workshop2.card.presentation.start

import ru.gb.android.workshop2.card.ServiceLocator

object FeatureServiceLocator {

    fun providePresenter(): ProductPresenter {
        return ProductPresenter(
            consumeFirstProductUseCase = ServiceLocator.provideConsumeProductsUseCase(),
            productVOFactory = provideProductVOMapper(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
        )
    }

    private fun provideProductVOMapper(): ProductVOFactory {
        return ProductVOFactory(
            discountFormatter = ServiceLocator.provideDiscountFormatter(),
            priceFormatter = ServiceLocator.providePriceFormatter(),
        )
    }
}