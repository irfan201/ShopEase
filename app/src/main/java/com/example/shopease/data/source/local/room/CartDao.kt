package com.example.shopease.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopease.data.model.ProductCartEntity


@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCart(product: ProductCartEntity)

    @Query("SELECT * FROM product_cart WHERE userId = :userId")
    suspend fun getProductCarts(userId: String): List<ProductCartEntity>

    @Delete
    suspend fun deleteProductCart(product: ProductCartEntity)


}