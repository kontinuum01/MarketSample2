package ru.gb.android.workshop2.presentation.card.start


import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {


    fun provideProductViewModelFactory(): ProductViewModelFactory {
        return ProductViewModelFactory(
            consumeFirstProductUseCase = ServiceLocator.provideConsumeFirstProductUseCase(),
            productStateFactory = provideProductStateFactory(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
        )
    }

    private fun provideProductStateFactory(): ProductStateFactory {
        return ProductStateFactory(
            discountFormatter = ServiceLocator.provideDiscountFormatter(),
            priceFormatter = ServiceLocator.providePriceFormatter(),
        )
    }
}