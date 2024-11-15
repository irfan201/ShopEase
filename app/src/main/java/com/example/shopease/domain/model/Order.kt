package com.example.shopease.domain.model

data class Order(
    val orId: Int,
    val orUsId: Int,
    val totalPrice: Int,
    val orToken: String,
    val orUrl: String,
)
