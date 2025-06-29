package ru.gb.android.workshop2.presentation.list.product

import android.content.Context

data class ScreenListState(
    val isLoading: Boolean = false,
    val productListState: ProductListState = ProductListState(),
    val hasError: Boolean = false,
    val getErrorText: (Context) -> String = {"" }
)

data class ProductListState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: String = "",
    val hasDiscount: Boolean = false,
    val discount: String = "",
)