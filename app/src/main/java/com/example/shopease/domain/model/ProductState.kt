package com.example.shopease.domain.model

sealed class ProductState {
    object Loading : ProductState()
    data class Success(val data: List<Product>) : ProductState()
    data class Error(val message: String) : ProductState()

}