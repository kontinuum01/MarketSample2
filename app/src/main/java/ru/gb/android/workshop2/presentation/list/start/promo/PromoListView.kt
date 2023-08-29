package ru.gb.android.workshop2.presentation.list.start.promo

interface PromoListView {
    fun showProgress()
    fun hideProgress()
    fun showPromos(promoList: List<PromoVO>)
    fun hidePromos()
    fun showError()
}
