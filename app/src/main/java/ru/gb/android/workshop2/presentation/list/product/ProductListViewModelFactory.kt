package ru.gb.android.workshop2.presentation.list.product


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class ProductListViewModelFactory(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductListViewModel(
            consumeProductsUseCase = consumeProductsUseCase,
            productStateFactory = productStateFactory,
            consumePromosUseCase = consumePromosUseCase
        ) as T

    }
}