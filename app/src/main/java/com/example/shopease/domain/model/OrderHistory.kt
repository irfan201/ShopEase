package com.example.shopease.domain.model

data class OrderHistory(
    val orId: Int,
    val totalPrice: Int,
    val nameOrder: String,
    val quantity: Int,
    val statusPayment: String,
)
