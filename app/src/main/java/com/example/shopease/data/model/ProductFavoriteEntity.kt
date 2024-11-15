package com.example.shopease.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_favorite")
data class ProductFavoriteEntity(
    @PrimaryKey
    val productId: Int,

    val productName: String,
    val productPrice: Int,
    val productDescription: String,
    val productCategory: String,
    val productImage: String,
    val productRating: Float,
    val userId: String,
    val isFavorite: Boolean
)