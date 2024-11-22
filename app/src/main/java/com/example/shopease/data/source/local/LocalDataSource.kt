package com.example.shopease.data.source.local

import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.data.source.local.preference.UserDataStore
import com.example.shopease.data.source.local.room.CartDao
import com.example.shopease.data.source.local.room.FavoriteDao

interface LocalDataSource {
    suspend fun saveLogin(isLogin: Boolean)
    fun getLogin(): Boolean
    suspend fun logout()
    suspend fun insertFavorite(favorite: ProductFavoriteEntity)
    suspend fun getFavorites(userId: String): List<ProductFavoriteEntity>
    suspend fun deleteFavorite(favorite: ProductFavoriteEntity)
    suspend fun insertProductCart(product: ProductCartEntity)
    suspend fun getProductCarts(userId: String): List<ProductCartEntity>
    suspend fun deleteProductCart(product: ProductCartEntity)
    suspend fun isFavorite(productId: Int, userId: String): Boolean
    suspend fun saveCategory(category: String)
    fun getCategory(): String?
    suspend fun saveStart(start: Boolean)
    fun getStart(): Boolean
    fun clearCategory()

}

class LocalDataSourceImpl(
    private val userDataStore: UserDataStore,
    private val favoriteDao: FavoriteDao,
    private val cartDao: CartDao
) : LocalDataSource {
    override suspend fun saveLogin(isLogin: Boolean) {
        userDataStore.saveLogin(isLogin)
    }

    override fun getLogin(): Boolean {
        return userDataStore.getLogin()
    }

    override suspend fun logout() {
        userDataStore.logout()
    }

    override suspend fun insertFavorite(favorite: ProductFavoriteEntity) {
        favoriteDao.insertFavorite(favorite)
    }

    override suspend fun getFavorites(userId: String): List<ProductFavoriteEntity> {
        return favoriteDao.getFavorites(userId)
    }

    override suspend fun deleteFavorite(favorite: ProductFavoriteEntity) {
        favoriteDao.deleteFavorite(favorite)
    }


    override suspend fun insertProductCart(product: ProductCartEntity) {
        cartDao.insertProductCart(product)
    }

    override suspend fun getProductCarts(userId: String): List<ProductCartEntity> {
        return cartDao.getProductCarts(userId)
    }

    override suspend fun deleteProductCart(product: ProductCartEntity) {
        cartDao.deleteProductCart(product)
    }

    override suspend fun isFavorite(productId: Int, userId: String): Boolean {
        return favoriteDao.isFavorite(productId, userId)
    }

    override suspend fun saveCategory(category: String) {
        userDataStore.saveCategory(category)
    }

    override fun getCategory(): String? {
        return userDataStore.getCategory()
    }

    override suspend fun saveStart(start: Boolean) {
        userDataStore.saveStart(start)
    }

    override fun getStart(): Boolean {
        return userDataStore.getStart()

    }

    override fun clearCategory() {
        userDataStore.clearCategory()
    }
}