package ru.gb.android.workshop2.presentation.list.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class PromoViewModel(
    private val promoStateMapper: PromoStateMapper,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _promoState = MutableStateFlow(PromoState())
    val promoState : StateFlow<PromoState> = _promoState.asStateFlow()

    fun loadPromos() {
        consumePromosUseCase()
            .map { promos ->
                promos.map(promoStateMapper::map)
            }

            .onEach {
                _promoState.update { promoState ->
                    promoState
                }
            }

            .launchIn(viewModelScope)
    }

}
