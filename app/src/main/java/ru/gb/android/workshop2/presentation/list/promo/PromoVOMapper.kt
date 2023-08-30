package ru.gb.android.workshop2.presentation.list.promo

import ru.gb.android.workshop2.domain.promo.Promo

class PromoVOMapper {
    fun map(promo: Promo): PromoVO {
        return PromoVO(
            id = promo.id,
            name = promo.name,
            image = promo.image,
            description = promo.description
        )
    }
}
