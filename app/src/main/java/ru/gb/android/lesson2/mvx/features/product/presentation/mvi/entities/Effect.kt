package ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities

sealed class Effect {
    object Loading : Effect()
    data class ProductLoaded(val product: ProductState) : Effect()
    data class Error(val throwable: Throwable) : Effect()
}