package com.example.shopease.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.ProductState
import com.example.shopease.domain.usecase.ProductUseCase
import com.example.shopease.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState: StateFlow<ProductState> get() = _productState


    suspend fun getProductsbyCategory(category: String) {
        try {
            val products = productUseCase.getProductsByCategory(category)
            _productState.value = ProductState.Success(products)
        } catch (e: Exception) {
            _productState.value = ProductState.Error(e.message ?: "Unknown error")
        }

    }

    fun saveCategory(category: String) {
        viewModelScope.launch {
            userUseCase.saveCategory(category)
        }
    }
    suspend fun getCategory(): String? {
        return userUseCase.getCategory()
    }

}