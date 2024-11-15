package com.example.shopease.domain.model

sealed class OrderHistoryState {
    object Loading : OrderHistoryState()
    data class Success(val data: List<OrderHistory>) : OrderHistoryState()
    data class Error(val message: String) : OrderHistoryState()


}