package com.example.shopease.presentation.home

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
class HomeViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState: StateFlow<ProductState> = _productState

    init {
        if (getCategory() != null){
            getCategory()?.let { getProductsbyCategory(it) }
        }else{
            getProducts()
        }
    }

    fun saveCategory(category: String) {
        viewModelScope.launch {
            userUseCase.saveCategory(category)
        }
    }

    fun getCategory(): String? {
        return userUseCase.getCategory()
    }

    fun clearCategory(){
        userUseCase.clearCategory()
    }


    fun getProducts() {
        viewModelScope.launch {
            try {
                val products = productUseCase.getProducts()
                _productState.value = ProductState.Success(products)
            } catch (e: Exception) {
                _productState.value = ProductState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun getProductsbyCategory(category: String) {
        viewModelScope.launch {
            try {
                val products = productUseCase.getProductsByCategory(category)
                _productState.value = ProductState.Success(products)
            } catch (e: Exception) {
                _productState.value = ProductState.Error(e.message ?: "Unknown error")
            }
        }
    }

}