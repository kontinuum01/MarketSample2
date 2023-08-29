package ru.gb.android.workshop2.card.presentation.start

import ru.gb.android.workshop2.card.domain.promo.Promo
import ru.gb.android.workshop2.card.domain.product.Product
import ru.gb.android.workshop2.card.presentation.common.DiscountFormatter
import ru.gb.android.workshop2.card.presentation.common.PriceFormatter

class ProductVOFactory(
    private val discountFormatter: DiscountFormatter,
    private val priceFormatter: PriceFormatter,
) {
    fun create(product: Product, promos: List<Promo>): ProductVO {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.id })
        }
        return ProductVO(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price.let(priceFormatter::format),
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