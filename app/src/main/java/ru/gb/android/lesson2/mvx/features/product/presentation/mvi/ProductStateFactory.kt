package ru.gb.android.lesson2.mvx.features.product.presentation.mvi

import ru.gb.android.lesson2.mvx.common.promo.domain.Promo
import ru.gb.android.lesson2.mvx.features.product.domain.Product
import ru.gb.android.lesson2.mvx.features.product.presentation.mvi.entities.ProductState

class ProductStateFactory {
    fun create(product: Product, promos: List<Promo>): ProductState {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.id })
        }
        return ProductState(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price,
            hasDiscount = promoForProduct != null,
            discount = (promoForProduct as? Promo.PromoForProducts)?.discount?.toInt() ?: 0
        )
    }
}