package com.example.shopease.presentation.orderHistoryDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.OrderDetailHistoryState
import com.example.shopease.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(private val productUseCase: ProductUseCase):ViewModel() {

    private val _orderDetailState = MutableStateFlow<OrderDetailHistoryState>(
        OrderDetailHistoryState.Loading)
    val orderDetailState: StateFlow<OrderDetailHistoryState> = _orderDetailState

    fun getOrderHistoryDetail(id: String) {
        viewModelScope.launch {
            try {
                val data = productUseCase.getOrderHistoryDetail(id)
                _orderDetailState.value = OrderDetailHistoryState.Success(data)
            }catch (e: Exception) {
                _orderDetailState.value = OrderDetailHistoryState.Error(e.message ?: "Unknown error")
            }
        }
    }
}