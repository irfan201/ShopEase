package com.example.shopease.data.model


import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("or_active")
        val orActive: Boolean,
        @SerializedName("or_created_by")
        val orCreatedBy: Int,
        @SerializedName("or_created_on")
        val orCreatedOn: String,
        @SerializedName("or_id")
        val orId: Int,
        @SerializedName("or_payment_status")
        val orPaymentStatus: String,
        @SerializedName("or_platform_id")
        val orPlatformId: String,
        @SerializedName("or_status")
        val orStatus: String,
        @SerializedName("or_tag")
        val orTag: String,
        @SerializedName("or_token_id")
        val orTokenId: String,
        @SerializedName("or_total_price")
        val orTotalPrice: Int,
        @SerializedName("or_updated_by")
        val orUpdatedBy: Any,
        @SerializedName("or_updated_on")
        val orUpdatedOn: String,
        @SerializedName("or_us_id")
        val orUsId: Int,
        @SerializedName("transaction")
        val transaction: Transaction
    ) {
        data class Transaction(
            @SerializedName("redirect_url")
            val redirectUrl: String,
            @SerializedName("token")
            val token: String
        )
    }
}