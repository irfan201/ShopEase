package com.example.shopease.domain.repository


import com.example.shopease.data.model.OrderRequest
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.model.OrderHistory
import com.example.shopease.domain.model.Product


interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getDetailProduct(id: Int): Product
    suspend fun getProductsByCategory(category: String): List<Product>
    suspend fun insertFavorite(favorite: ProductFavoriteEntity)
    suspend fun getFavorites(userId: String): List<Product>
    suspend fun deleteFavorite(favorite: ProductFavoriteEntity)
    suspend fun insertProductCart(product: ProductCartEntity)
    suspend fun getProductsCarts(userId: String): List<Product>
    suspend fun deleteProductCart(product: ProductCartEntity)
    suspend fun getProductsbySearch(search: String): List<Product>
    suspend fun orderProduct(orderRequest: OrderRequest): Order
    suspend fun isFavorite(productId: Int, userId: String): Boolean
    suspend fun getOrderHistory(email:String): List<OrderHistory>
    suspend fun getOrderHistoryDetail(id: String): List<OrderHistory.OrderDetail>
}

