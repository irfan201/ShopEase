package com.example.shopease.domain.repository

import com.example.shopease.data.model.OrderRequest
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.data.source.local.LocalDataSource
import com.example.shopease.data.source.remote.RemoteDataSource
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.model.OrderHistory
import com.example.shopease.domain.model.Product
import com.example.shopease.utils.DataMapper.toOrder
import com.example.shopease.utils.DataMapper.toOrderDetail
import com.example.shopease.utils.DataMapper.toOrderHistory
import com.example.shopease.utils.DataMapper.toProduct
import javax.inject.Inject

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
        return localDataSource.isFavorite(productId, userId)
    }

    override suspend fun getOrderHistory(email: String): List<OrderHistory> {
        return remoteDataSource.getOrderHistory(email).data.map { it.toOrderHistory() }
    }

    override suspend fun getOrderHistoryDetail(id: String): List<OrderHistory.OrderDetail> {
        return remoteDataSource.getOrderHistoryDetail(id).data.details.first().odProducts.map { it.toOrderDetail() }
    }

}
