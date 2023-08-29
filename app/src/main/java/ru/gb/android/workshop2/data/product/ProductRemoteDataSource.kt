package ru.gb.android.workshop2.data.product

class ProductRemoteDataSource(
    private val productApiService: ProductApiService,
) {
    suspend fun getProducts(): List<ProductDto> {
        return productApiService.getProducts()
    }
}
