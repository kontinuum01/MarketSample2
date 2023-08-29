package ru.gb.android.workshop2.presentation.list.start.product

import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {

    fun providePresenter(): ProductListPresenter {
        return ProductListPresenter(
            consumeProductsUseCase = ServiceLocator.provideConsumeProductsUseCase(),
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