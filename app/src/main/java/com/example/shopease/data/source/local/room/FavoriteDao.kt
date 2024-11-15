package com.example.shopease.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopease.data.model.ProductFavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: ProductFavoriteEntity)

    @Query("SELECT * FROM product_favorite WHERE userId = :userId")
    suspend fun getFavorites(userId: String): List<ProductFavoriteEntity>

    @Delete
    suspend fun deleteFavorite(favorite: ProductFavoriteEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM product_favorite WHERE productId = :productId AND userId = :userId)")
    suspend fun isFavorite(productId: Int, userId: String): Boolean

}