package com.example.shopease.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val category: String,
    val image: List<String>,
    var quantity: Int = 1,
    val rating: Float,
    val userId :String? = null,
    val totalPrice: Int? = null,
    val isFavorite: Boolean? = null
):Parcelable
