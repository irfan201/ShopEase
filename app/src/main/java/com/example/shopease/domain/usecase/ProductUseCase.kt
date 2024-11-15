package com.example.shopease.domain.usecase

import com.example.shopease.data.model.OrderRequest
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.model.OrderHistory
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.repository.ProductRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend fun getProducts(): List<Product> {
        return productRepository.getProducts()
    }

    suspend fun getDetailProduct(id: Int): Product {
        return productRepository.getDetailProduct(id)
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        return productRepository.getProductsByCategory(category)
    }

    suspend fun insertFavorite(favorite: ProductFavoriteEntity) {
        productRepository.insertFavorite(favorite)
    }

    suspend fun getFavorites(userId: String): List<Product> {
        return productRepository.getFavorites(userId)
    }

    suspend fun deleteFavorite(favorite: ProductFavoriteEntity) {
        productRepository.deleteFavorite(favorite)

    }

    suspend fun insertProductCart(productCartEntity: ProductCartEntity) {
        productRepository.insertProductCart(productCartEntity)
    }

    suspend fun getProductCarts(userId: String): List<Product> {
        return productRepository.getProductsCarts(userId)
    }

    suspend fun deleteProductCart(productCartEntity: ProductCartEntity) {
        productRepository.deleteProductCart(productCartEntity)
    }

    suspend fun getProductsbySearch(search: String): List<Product> {
        return productRepository.getProductsbySearch(search)

    }

    suspend fun orderProduct(orderRequest: OrderRequest): Order {
        return productRepository.orderProduct(orderRequest)
    }

    suspend fun isFavorite(productId: Int, userId: String): Boolean {
        return productRepository.isFavorite(productId, userId)
    }

    suspend fun getOrderHistory(): List<OrderHistory> {
        return productRepository.getOrderHistory()
    }


}