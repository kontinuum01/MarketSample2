package ru.gb.android.workshop2.presentation.list.start.product

interface ProductListView {
    fun showProgress()
    fun hideProgress()
    fun hidePullToRefresh()
    fun showProducts(productList: List<ProductVO>)
    fun hideProducts()
    fun showError()
}
