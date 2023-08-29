package ru.gb.android.workshop2.card.presentation.finish

import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.card.ServiceLocator

object FeatureServiceLocator {

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ProductsViewModelFactory(
            consumeFirstProductUseCase = ServiceLocator.provideConsumeProductsUseCase(),
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