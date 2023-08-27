package ru.gb.android.lesson2.mvx.features.product.presentation.mvp2

import ru.gb.android.lesson2.mvx.features.product.presentation.ProductVO

interface ProductView {
    fun showProgress()
    fun hideProgress()

    fun showProduct(product: ProductVO)
    fun hideProduct()

    fun showError()
}
