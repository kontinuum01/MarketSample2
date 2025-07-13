package ru.gb.android.workshop2.presentation.list.product

import android.content.Context
import android.util.Log

data class ScreenListState(
    val isLoading: Boolean = false,
    val productListState: ProductListState = ProductListState(),
    val hasError: Boolean = false,
    val getErrorText: (Context) -> String = { "" }
)

data class ProductListState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: String = "",
    val hasDiscount: Boolean = false,
    val discount: String = "",
) {
     init {
         Log.d("ProductListStateInit","id = $id, name = $name, image = $image, price = $price, discount = $discount")
     }
}