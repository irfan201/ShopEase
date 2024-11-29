package com.example.shopease.presentation.detailProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.domain.model.ProductDetailState
import com.example.shopease.domain.model.User
import com.example.shopease.domain.usecase.ProductUseCase
import com.example.shopease.domain.usecase.UserUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(private val productUseCase: ProductUseCase,private val userUseCase: UserUseCase) :ViewModel() {

    private val _productDetailState = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val productDetailState: StateFlow<ProductDetailState> get() = _productDetailState

    fun getDetailProduct(id: Int) {
        viewModelScope.launch {
            try {
                val data = productUseCase.getDetailProduct(id)
                _productDetailState.value = ProductDetailState.Success(data)
            } catch (e: Exception) {
                _productDetailState.value = ProductDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun insertFavorite(favorite: ProductFavoriteEntity) {
        viewModelScope.launch {
            productUseCase.insertFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: ProductFavoriteEntity) {
        viewModelScope.launch {
            productUseCase.deleteFavorite(favorite)
        }
    }

    fun getCurrentUser(): User? {
        return userUseCase.getCurrentUser()
    }


    fun insertProductCart(product: ProductCartEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.insertProductCart(product)
            val user = withContext(Dispatchers.Default){
                userUseCase.getCurrentUser()
            }

        }

    }

    suspend fun isFavorite(productId: Int, userId: String): Boolean {
        return productUseCase.isFavorite(productId, userId)

    }



}