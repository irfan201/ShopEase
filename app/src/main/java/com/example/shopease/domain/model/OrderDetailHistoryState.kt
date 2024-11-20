package com.example.shopease.domain.model

sealed class OrderDetailHistoryState {
    object Loading : OrderDetailHistoryState()
    data class Success(val data: List<OrderHistory.OrderDetail>) : OrderDetailHistoryState()
    data class Error(val message: String) : OrderDetailHistoryState()
}