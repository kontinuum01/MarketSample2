package ru.gb.android.workshop2.card.domain.product

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.gb.android.workshop2.card.data.product.ProductRepository

class ConsumeFirstProductUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper,
) {
    operator fun invoke(): Flow<Product> {
        return productRepository.consumeProducts()
            .onEach {
                // imitate some background work
                delay(1000)
            }
            .filter { list -> list.isNotEmpty() }
            .map { list -> list.first() }
            .map { product -> productDomainMapper.fromEntity(product) }
    }
}