package ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities

data class State(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val product: ProductState = ProductState(),
)

data class ProductState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: Double = 0.0,
    val hasDiscount: Boolean = false,
    val discount: Int = 0,
)
