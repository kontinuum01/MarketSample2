package ru.gb.android.workshop2.presentation.card.start

interface ProductView {
    fun showProgress()
    fun hideProgress()
    fun showImage(image: String)
    fun hideImage()
    fun showName(name: String)
    fun hideName()
    fun showPrice(price: String)
    fun hidePrice()
    fun showPromo(discount: String)
    fun hidePromo()
    fun showAddToCart()
    fun hideAddToCart()
    fun showError()
}
