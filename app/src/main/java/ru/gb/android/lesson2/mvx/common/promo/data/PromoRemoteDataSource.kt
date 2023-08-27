package ru.gb.android.lesson2.mvx.common.promo.data

import ru.gb.android.lesson2.mvx.common.promo.data.PromoApiService
import ru.gb.android.lesson2.mvx.common.promo.data.PromoDto

class PromoRemoteDataSource(
    private val promoApiService: PromoApiService,
) {
    suspend fun getPromos(): List<PromoDto> {
        return promoApiService.getPromos()
    }
}
