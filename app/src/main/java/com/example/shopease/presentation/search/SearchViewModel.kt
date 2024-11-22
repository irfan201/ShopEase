package com.example.shopease.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.ProductState
import com.example.shopease.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productUseCase: ProductUseCase):ViewModel() {

    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState: StateFlow<ProductState> = _productState


    init {
        getProducts()
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

    fun getProductsBySearch(search: String) {
        viewModelScope.launch {
            try {
                val products = productUseCase.getProductsbySearch(search)
                _productState.value = ProductState.Success(products)
            } catch (e: Exception) {
                _productState.value = ProductState.Error(e.message ?: "Unknown error")
            }
        }

    }
}