package ru.gb.android.workshop2.data.product

import kotlinx.serialization.Serializable

@Serializable
class ProductEntity (
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
)