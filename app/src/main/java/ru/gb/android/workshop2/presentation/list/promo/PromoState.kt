package ru.gb.android.workshop2.presentation.list.promo

import android.content.Context

data class ScreenListState(
    val isLoading: Boolean = false,
    val promoListState: List<PromoState> = emptyList(),
    val hasError: Boolean = false,
    val getErrorText: (Context) -> String = { "Произошла ошибка" }
) {
    val promoList: List<PromoState>
        get() {
            return promoListState
        }
}

data class PromoState (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val image: String = "",
)




