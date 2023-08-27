package ru.gb.android.lesson2.mvx.features.product.presentation.mvp1

interface ProductView {
    fun showProgress()
    fun hideProgress()
    fun showImage(image: String)
    fun hideImage()
    fun showName(name: String)
    fun hideName()
    fun showPrice(price: Double)
    fun hidePrice()
    fun showPromo(discount: Int)
    fun hidePromo()
    fun showAddToCart()
    fun hideAddToCart()
    fun showError()
}
