package ru.gb.android.lesson2.mvx.features.product.data

class ProductRemoteDataSource(
    private val productApiService: ProductApiService,
) {
    suspend fun getProducts(): List<ProductDto> {
        return productApiService.getProducts()
    }
}
