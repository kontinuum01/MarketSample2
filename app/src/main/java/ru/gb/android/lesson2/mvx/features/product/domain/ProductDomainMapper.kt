package ru.gb.android.lesson2.mvx.features.product.domain

import ru.gb.android.lesson2.mvx.features.product.data.ProductDto
import ru.gb.android.lesson2.mvx.features.product.data.ProductEntity

class ProductDomainMapper {
    fun fromDto(productDto: ProductDto): Product {
        return Product(
            id = productDto.id,
            name = productDto.name,
            image = productDto.image,
            price = productDto.price
        )
    }

    fun fromEntity(productEntity: ProductEntity): Product {
        return Product(
            id = productEntity.id,
            name = productEntity.name,
            image = productEntity.image,
            price = productEntity.price
        )
    }

    fun toEntity(product: Product): ProductEntity {
        return ProductEntity(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price
        )
    }
}