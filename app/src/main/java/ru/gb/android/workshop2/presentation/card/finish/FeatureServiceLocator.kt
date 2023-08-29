package ru.gb.android.workshop2.presentation.card.finish

import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ProductsViewModelFactory(
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