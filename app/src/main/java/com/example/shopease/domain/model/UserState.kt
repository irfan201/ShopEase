package com.example.shopease.domain.model


sealed class UserState {
    object Loading : UserState()
    data class Success(val user: User?) : UserState()
    data class Error(val message: String) : UserState()

}