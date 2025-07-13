package ru.gb.android.workshop2.presentation.card.start

import androidx.lifecycle.ViewModel
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
import ru.gb.android.workshop2.domain.product.ConsumeFirstProductUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.marketsample.R


class ProductViewModel(
    private val consumeFirstProductUseCase: ConsumeFirstProductUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState())
    val state: StateFlow<ScreenState> = _state.asStateFlow()

    fun loadProduct() {
        combine(
            consumeFirstProductUseCase(),
            consumePromosUseCase(),
        ) { product, promos -> productStateFactory.create(product, promos) }
            .onStart {
                _state.update { screenState ->
                    screenState.copy(isLoading = true)
                }

            }

            .onEach { productState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        productState = productState
                    )
                }
            }
            .catch {
                sheduleRefresh()
                _state.update { screenState ->
                    screenState.copy(
                        hasError = true,
                        getErrorText = {context -> context.getString(R.string.error_wile_loading_data) }
                    )

                }
            }
            .launchIn(viewModelScope)
    }

    fun errorShown(){
        _state.update { screenState -> screenState.copy(hasError = false) }
    }

    private suspend fun sheduleRefresh() {
        viewModelScope.launch {
            delay(3000)
            loadProduct()
        }
    }
}


