package ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities

sealed class News {
    data class ErrorExecutingRequest(val throwable: Throwable) : News()
}