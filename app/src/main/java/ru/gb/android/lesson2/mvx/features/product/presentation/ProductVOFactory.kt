package ru.gb.android.lesson2.mvx.features.product.presentation

import ru.gb.android.lesson2.mvx.common.promo.domain.Promo
import ru.gb.android.lesson2.mvx.features.product.domain.Product

class ProductVOFactory {
    fun create(product: Product, promos: List<Promo>): ProductVO {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.id })
        }
        return ProductVO(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price,
            hasDiscount = promoForProduct != null,
            discount = (promoForProduct as? Promo.PromoForProducts)?.discount?.toInt() ?: 0
        )
    }
}