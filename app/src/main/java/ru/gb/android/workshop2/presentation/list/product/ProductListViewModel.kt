package ru.gb.android.workshop2.presentation.list.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.marketsample.R

class ProductListViewModel (
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ScreenListState())
    val state: StateFlow<ScreenListState> = _state.asStateFlow()

    fun loadProduct() {
        combine(
            consumeProductsUseCase(),
            consumePromosUseCase(),
        ) { product, promos -> productStateFactory.create(product, promos) }
            .onStart {
                setLoadingState(true)
            }
            .onEach { productListState ->
                updateState { it.copy(isLoading = false, productListState = listOf(productListState)) }
                Log.d("ProductListViewModel", "products: $productListState")
            }
            .catch { error ->
                Log.e("ProductListViewModel", "Error loading products", error)
                scheduleRefresh()
                updateState {
                    it.copy(
                        hasError = true,
                        getErrorText = { context -> context.getString(R.string.error_wile_loading_data) }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun scheduleRefresh() {
        viewModelScope.launch {
            delay(3000)
            loadProduct()
        }
    }

    fun errorShown() {
        updateState { it.copy(hasError = false) }
    }

    private fun setLoadingState(isLoading: Boolean) {
        updateState { it.copy(isLoading = isLoading) }
    }

    private fun updateState(update: (ScreenListState) -> ScreenListState) {
        _state.update(update)
    }
}