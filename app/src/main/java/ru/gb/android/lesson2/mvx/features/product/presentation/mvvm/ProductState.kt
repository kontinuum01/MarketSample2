package ru.gb.android.lesson2.mvx.features.product.presentation.mvvm

//sealed interface ProductState {
//    object Loading: ProductState
//    data class Loaded(
//        val id: String = "",
//        val name: String = "",
//        val image: String = "",
//        val price: Double = 0.0,
//        val hasDiscount: Boolean = false,
//        val discount: Int = 0,
//    ): ProductState
//}

data class ProductState(
    val isLoading: Boolean = false,
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: Double = 0.0,
    val hasDiscount: Boolean = false,
    val discount: Int = 0,
)