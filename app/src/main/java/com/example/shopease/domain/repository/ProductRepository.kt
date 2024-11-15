package com.example.shopease.domain.repository

import com.example.shopease.data.model.OrderDto
import com.example.shopease.data.model.OrderHistoryDto
import com.example.shopease.data.model.OrderRequest
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductCategoryDto
import com.example.shopease.data.model.ProductDetailDto
import com.example.shopease.data.model.ProductDto
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.data.source.local.LocalDataSource
import com.example.shopease.data.source.remote.RemoteDataSource
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.model.OrderHistory
import com.example.shopease.domain.model.Product
import javax.inject.Inject

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
    suspend fun getOrderHistory(): List<OrderHistory>
}

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return remoteDataSource.getProducts().data.map { it.toProduct() }
    }

    override suspend fun getDetailProduct(id: Int): Product {
        return remoteDataSource.getDetailProduct(id).data.toProduct()

    }

    override suspend fun getProductsByCategory(category: String): List<Product> {
        return remoteDataSource.getProductsByCategory(category).data.flatMap { dataItem ->
            dataItem.products.map { product ->
                product.toProduct(dataItem.ctName)
            }
        }
    }

    override suspend fun insertFavorite(favorite: ProductFavoriteEntity) {
        localDataSource.insertFavorite(favorite)
    }

    override suspend fun getFavorites(userId: String): List<Product> {
        return localDataSource.getFavorites(userId).map { it.toProduct() }
    }

    override suspend fun deleteFavorite(favorite: ProductFavoriteEntity) {
        localDataSource.deleteFavorite(favorite)
    }

    override suspend fun insertProductCart(product: ProductCartEntity) {
        localDataSource.insertProductCart(product)
    }

    override suspend fun getProductsCarts(userId: String): List<Product> {
        return localDataSource.getProductCarts(userId).map { it.toProduct() }
    }

    override suspend fun deleteProductCart(product: ProductCartEntity) {
        localDataSource.deleteProductCart(product)
    }

    override suspend fun getProductsbySearch(search: String): List<Product> {
        return remoteDataSource.getProductbBySearch(search).data.map { it.toProduct() }
    }

    override suspend fun orderProduct(orderRequest: OrderRequest): Order {
        return remoteDataSource.orderProduct(orderRequest).data.toOrder()
    }

    override suspend fun isFavorite(productId: Int, userId: String): Boolean {
        return localDataSource.isFavorite(productId,userId)
    }

    override suspend fun getOrderHistory(): List<OrderHistory> {
        return remoteDataSource.getOrderHistory().data.map { it.toOrderHistory() }
    }


    private fun ProductDto.Data.toProduct(): Product {
        return Product(
            id = pdId,
            title = pdName,
            price = pdPrice,
            description = pdDescription,
            category = categories.joinToString { it.ctName },
            image = listOf(pdImageUrl, pdData.imageUrl2, pdData.imageUrl3),
            rating = rating
        )
    }

    private fun ProductDetailDto.Data.toProduct(): Product {
        return Product(
            id = pdId,
            title = pdName,
            price = pdPrice,
            description = pdDescription,
            category = categories.joinToString { it.ctName },
            image = listOf(pdImageUrl, pdData.imageUrl2, pdData.imageUrl3),
            rating = reviews.first().rvRating,
        )
    }

    private fun ProductCategoryDto.Data.Product.toProduct(category: String): Product {
        return Product(
            id = pdId,
            title = pdName,
            price = pdPrice,
            description = pdDescription,
            category = category,
            image = listOf(pdImageUrl, pdData.imageUrl2, pdData.imageUrl3),
            rating = rating
        )

    }

    private fun ProductFavoriteEntity.toProduct(): Product {
        return Product(
            id = productId,
            title = productName,
            price = productPrice,
            description = productDescription,
            category = productCategory,
            image = listOf(productImage),
            rating = productRating,
            userId = userId,
            isFavorite = isFavorite
        )

    }

    private fun ProductCartEntity.toProduct(): Product {
        return Product(
            id = productId,
            title = productName,
            price = productPrice,
            description = productDescription,
            category = productCategory,
            image = listOf(productImage),
            rating = productRating,
            userId = userId
        )
    }

    private fun OrderDto.Data.toOrder(): Order {
        return Order(
            orId = orId,
            orUsId = orUsId,
            totalPrice = orTotalPrice,
            orToken = transaction.token,
            orUrl = transaction.redirectUrl
        )
    }

    private fun OrderHistoryDto.Data.toOrderHistory(): OrderHistory {
        return OrderHistory(
            orId = orId,
            totalPrice = orTotalPrice,
            nameOrder = details.first().odProducts.first().name,
            quantity = details.first().odProducts.first().quantity,
            statusPayment = orStatus
        )

    }

}