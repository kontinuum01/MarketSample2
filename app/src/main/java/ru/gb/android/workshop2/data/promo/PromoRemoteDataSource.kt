package ru.gb.android.workshop2.data.promo

class PromoRemoteDataSource(
    private val promoApiService: PromoApiService,
) {
    suspend fun getPromos(): List<PromoDto> {
        return promoApiService.getPromos()
    }
}
