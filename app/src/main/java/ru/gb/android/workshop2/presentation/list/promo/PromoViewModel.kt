package ru.gb.android.workshop2.presentation.list.promo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import ru.gb.android.workshop2.marketsample.R

class PromoViewModel(
    private val promoStateMapper: PromoStateMapper,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _promoState = MutableStateFlow(ScreenListState())
    val promoState: StateFlow<ScreenListState> = _promoState.asStateFlow()

    fun loadPromos() {
        consumePromosUseCase()
            .onStart {
                setLoadingState(true)
            }
            .map { promos ->
                Log.d("PromoListViewModel", "promos: $promos")
                promos.map(promoStateMapper::map)

            }
            .onEach { promoListState ->
                updateState { it.copy(isLoading = false, promoListState = promoListState) }
                Log.d("PromoListViewModel", "products: $promoListState")
            }
            .catch { error ->
                Log.e("PromoListViewModel", "Error loading promo", error)
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
            loadPromos()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        updateState { it.copy(isLoading = isLoading) }
    }

    private fun updateState(update: (ScreenListState) -> ScreenListState) {
        _promoState.update(update)
    }


    fun errorShown() {
        updateState {
            it.copy(hasError = false)
        }
    }
}
