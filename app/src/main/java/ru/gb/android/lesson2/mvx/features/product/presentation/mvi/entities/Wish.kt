package ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities

sealed class Wish {
    object LoadProduct: Wish()
    data class AddToCart(val id: String): Wish()
}