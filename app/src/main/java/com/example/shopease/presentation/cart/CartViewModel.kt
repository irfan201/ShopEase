package com.example.shopease.presentation.cart

import androidx.lifecycle.ViewModel
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.domain.model.ProductState
import com.example.shopease.domain.model.User
import com.example.shopease.domain.usecase.ProductUseCase
import com.example.shopease.domain.usecase.UserUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState : StateFlow<ProductState> get() =  _productState

    suspend fun getProductCarts(userId: String){
        try {
            val products = productUseCase.getProductCarts(userId)
            _productState.value = ProductState.Success(products)
        }catch (e: Exception){
            _productState.value = ProductState.Error(e.message ?: "Unknown error")
        }
    }

    fun getCurrentUser():User?{
        return userUseCase.getCurrentUser()
    }

    suspend fun deleteProductCart(productCartEntity: ProductCartEntity){
        productUseCase.deleteProductCart(productCartEntity)
    }
}