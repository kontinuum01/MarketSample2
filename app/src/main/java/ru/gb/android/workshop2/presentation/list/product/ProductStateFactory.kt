package ru.gb.android.workshop2.presentation.list.product

import ru.gb.android.workshop2.domain.product.Product
import ru.gb.android.workshop2.domain.promo.Promo
import ru.gb.android.workshop2.presentation.common.DiscountFormatter
import ru.gb.android.workshop2.presentation.common.PriceFormatter


class ProductStateFactory(
    private val discountFormatter: DiscountFormatter,
    private val priceFormatter: PriceFormatter,
) {
    fun create(product: List<Product>, promos: List<Promo>): ProductListState {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.map { it.id }.toString()})
        }
        return ProductListState(
            id = product.map { it.id }.toString(),
            name = product.map {it.name}.toString() ,
            image = product.map {it.image}.toString() ,
            price = product.map{ it.price}.toString(),
            hasDiscount = promoForProduct != null,
            discount = promoForProduct.resolveDiscount(),
        )
    }

    private fun Promo?.resolveDiscount(): String {
        return (this as? Promo.PromoForProducts)
            ?.discount
            ?.toInt()
            ?.let(discountFormatter::format)
            ?: ""
    }

    }







