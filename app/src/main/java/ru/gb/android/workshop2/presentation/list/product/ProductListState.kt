package ru.gb.android.workshop2.presentation.list.product

import android.content.Context

data class ScreenListState(
    val isLoading: Boolean = false,
    val productListState: List<ProductListState> = emptyList(),
    val hasError: Boolean = false,
    val getErrorText: (Context) -> String = { "Произошла ошибка" }
) {
    val productList: List<ProductListState>
        get() {
           return productListState
        }
}

data class ProductListState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: String = "",
    val hasDiscount: Boolean = false,
    val discount: String = "",
)

