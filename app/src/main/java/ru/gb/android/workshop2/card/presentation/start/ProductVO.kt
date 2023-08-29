package ru.gb.android.workshop2.card.presentation.start

data class ProductVO(
    val id: String,
    val name: String,
    val image: String,
    val price: String,
    val hasDiscount: Boolean,
    val discount: String,
)
