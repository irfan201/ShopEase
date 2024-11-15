package com.example.shopease.domain.model

sealed class OrderState {
    object Loading : OrderState()
    data class Success(val order: Order) : OrderState()
    data class Error(val message: String) : OrderState()

}