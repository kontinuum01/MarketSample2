package ru.gb.android.lesson2.mvx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import ru.gb.android.lesson2.mvx.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.lesson2.mvx.features.product.domain.ConsumeFirstProductUseCase
import ru.gb.android.lesson2.mvx.features.product.presentation.ProductVOFactory
import ru.gb.android.lesson2.mvx.features.product.presentation.mvvm.ProductStateFactory
import ru.gb.android.lesson2.mvx.features.product.presentation.mvvm.ProductsViewModel

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

                val savedStateHandle = extras.createSavedStateHandle()

                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(
                    consumeFirstProductUseCase = consumeFirstProductUseCase,
                    productStateFactory = productStateFactory,
                    consumePromosUseCase = consumePromosUseCase,
                    //savedStateHandle = savedStateHandle,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
