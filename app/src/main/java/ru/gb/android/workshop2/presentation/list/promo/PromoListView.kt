package ru.gb.android.workshop2.presentation.list.promo

interface PromoListView {
    fun showProgress()
    fun hideProgress()
    fun showPromos(promoList: List<PromoVO>)
    fun hidePromos()
    fun showError()
}
