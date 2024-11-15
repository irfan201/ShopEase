package com.example.shopease.domain.model

sealed class ProductDetailState {
    object Loading : ProductDetailState()
    data class Success(val data: Product) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()

}