package com.example.shopease.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.data.model.OrderRequest
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.model.OrderState
import com.example.shopease.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(private val productUseCase: ProductUseCase) :
    ViewModel() {
    private val _orderState = MutableStateFlow<OrderState>(OrderState.Loading)
    val orderState: StateFlow<OrderState> get() = _orderState

    fun orderProduct(orderRequest: OrderRequest){
        viewModelScope.launch {
            try {
                val order = productUseCase.orderProduct(orderRequest)
                _orderState.value = OrderState.Success(order)
            } catch (e: Exception) {
                _orderState.value = OrderState.Error(e.message ?: "Unknown error")

            }
        }
    }
}