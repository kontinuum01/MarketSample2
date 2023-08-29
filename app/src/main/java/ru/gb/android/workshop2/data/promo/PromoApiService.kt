package ru.gb.android.workshop2.data.promo

import retrofit2.http.GET

interface PromoApiService {
    @GET("test_api_promo.json")
    suspend fun getPromos(): List<PromoDto>
}
