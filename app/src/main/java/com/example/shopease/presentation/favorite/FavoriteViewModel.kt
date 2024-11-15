package com.example.shopease.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.domain.model.ProductState
import com.example.shopease.domain.model.User
import com.example.shopease.domain.usecase.ProductUseCase
import com.example.shopease.domain.usecase.UserUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState: StateFlow<ProductState> = _productState

    fun getFavorites(userId: String) {
        viewModelScope.launch {
            try {
                val products = productUseCase.getFavorites(userId)
                _productState.value = ProductState.Success(products)
            } catch (e: Exception) {
                _productState.value = ProductState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getCurrentUser(): User? {
        return userUseCase.getCurrentUser()
    }

}