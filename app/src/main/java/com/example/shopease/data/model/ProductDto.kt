package com.example.shopease.data.model


import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("totalProducts")
    val totalProducts: Int
) {
    data class Data(
        @SerializedName("categories")
        val categories: List<Category>,
        @SerializedName("pd_data")
        val pdData: PdData,
        @SerializedName("pd_description")
        val pdDescription: String,
        @SerializedName("pd_id")
        val pdId: Int,
        @SerializedName("pd_image_url")
        val pdImageUrl: String,
        @SerializedName("pd_name")
        val pdName: String,
        @SerializedName("pd_price")
        val pdPrice: Int,
        @SerializedName("pd_quantity")
        val pdQuantity: Int,
        @SerializedName("total_average_rating")
        val rating:Float,
        @SerializedName("total_reviews")
        val reviews:String
    ) {
        data class Category(
            @SerializedName("ct_id")
            val ctId: Int,
            @SerializedName("ct_name")
            val ctName: String
        )

        data class PdData(
            @SerializedName("image_url_2")
            val imageUrl2: String,
            @SerializedName("image_url_3")
            val imageUrl3: String
        )
    }
}