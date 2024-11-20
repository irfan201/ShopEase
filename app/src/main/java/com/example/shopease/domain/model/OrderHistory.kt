package com.example.shopease.domain.model

data class OrderHistory(
    val orId: String,
    val totalPrice: Int,
    val orderDetail: List<OrderDetail>,
    val dateOrder : String,
    val statusPayment: String,
){
    data class OrderDetail(
        val id: Int,
        val name: String,
        val price: Int,
        val quantity: Int,
        val imageUrl: String
    )
}
