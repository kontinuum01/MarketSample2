package ru.gb.android.workshop2.domain.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import ru.gb.android.workshop2.data.product.ProductRepository

class ConsumeFirstProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper,
) {
    operator fun invoke(): Flow<Product> {
        return productRepository.consumeProducts()
            .filter { list -> list.isNotEmpty() }
            .map { list -> list.first() }
            .map { product -> productDomainMapper.fromEntity(product) }
    }
}