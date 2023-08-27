package ru.gb.android.lesson2.mvx.features.product.presentation.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.gb.android.lesson2.mvx.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.lesson2.mvx.features.product.domain.ConsumeFirstProductUseCase

class ProductsViewModel(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    private val _isError = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val isError: SharedFlow<Boolean> = _isError.asSharedFlow()

    init {
        requestProducts()
    }

    private fun requestProducts() {
        _state.update { it.copy(isLoading = true) }
        consumeFirstProductUseCase()
            .flatMapLatest { product ->
                consumePromosUseCase().map { promos -> product to promos }
            }
            .map { (product, promos) ->
                productStateFactory.create(product, promos)
            }
            .onEach { productState ->
                _state.value = productState
            }
            .catch {
                _isError.tryEmit(true)
            }
            .launchIn(viewModelScope)
    }
}
