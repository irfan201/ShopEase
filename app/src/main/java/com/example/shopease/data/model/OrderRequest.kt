package com.example.shopease.data.model


import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("items")
    val items: List<Item>
) {
    data class Item(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("quantity")
        val quantity: Int
    )
}