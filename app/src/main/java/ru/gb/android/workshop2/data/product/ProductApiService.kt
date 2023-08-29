package ru.gb.android.workshop2.data.product

import retrofit2.http.GET

interface ProductApiService {
    @GET("test_api_products.json")
    suspend fun getProducts(): List<ProductDto>
}
