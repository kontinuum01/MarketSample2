package ru.gb.android.workshop2.data.promo

class PromoDataMapper {
    fun toEntity(promoDto: PromoDto): PromoEntity {
        return PromoEntity(
            id = promoDto.id,
            name = promoDto.name,
            image = promoDto.image,
            discount = promoDto.discount,
            description = promoDto.description,
            type = promoDto.type,
            products = promoDto.products ?: emptyList(),
        )
    }
}
