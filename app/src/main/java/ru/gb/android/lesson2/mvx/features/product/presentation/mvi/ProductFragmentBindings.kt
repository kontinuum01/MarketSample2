package ru.gb.android.lesson2.mvx.features.product.presentation.mvi

import com.badoo.mvicore.android.AndroidBindings

class ProductFragmentBindings(
    view: ProductFragment,
    private val feature: Feature,
    private val newsListener: NewsListener
) : AndroidBindings<ProductFragment>(view) {

    override fun setup(view: ProductFragment) {
        binder.bind(feature to view)
        binder.bind(feature.news to newsListener)
    }
}