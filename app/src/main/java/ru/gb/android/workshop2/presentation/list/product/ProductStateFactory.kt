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
        val productIds = product.map { it.id }
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId in productIds} )
        }

        val idsString = productIds.joinToString(", ")
        val namesString = product.joinToString(", ") { it.name }
        val imagesString = product.joinToString(", ") { it.image }
        val pricesString = product.joinToString(", ") { priceFormatter.format(it.price) }

        return ProductListState(
            id = idsString,
            name = namesString,
            image = imagesString,
            price = pricesString,
            hasDiscount = promoForProduct != null,
            discount = promoForProduct.resolveDiscount()
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







