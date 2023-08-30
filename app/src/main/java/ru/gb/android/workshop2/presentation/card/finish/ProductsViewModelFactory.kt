package ru.gb.android.workshop2.presentation.card.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.gb.android.workshop2.domain.product.ConsumeFirstProductUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class ProductsViewModelFactory(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(
                    consumeFirstProductUseCase = consumeFirstProductUseCase,
                    productStateFactory = productStateFactory,
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
