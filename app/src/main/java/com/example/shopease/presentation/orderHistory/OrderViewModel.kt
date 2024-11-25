package com.example.shopease.presentation.orderHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.OrderHistoryState
import com.example.shopease.domain.usecase.ProductUseCase
import com.example.shopease.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _orderHistoryState = MutableStateFlow<OrderHistoryState>(OrderHistoryState.Loading)
    val orderHistoryState: StateFlow<OrderHistoryState> get() = _orderHistoryState

    init {
        getOrderHistory(getCurrenUser()?.email ?: "test@gmail.com")
    }

     fun getCurrenUser()  = userUseCase.getCurrentUser()

    fun getOrderHistory(email: String) {
        viewModelScope.launch {
            try {
                val orderHistory = productUseCase.getOrderHistory(email)
                _orderHistoryState.value = OrderHistoryState.Success(orderHistory)
            } catch (e: Exception) {
                _orderHistoryState.value = OrderHistoryState.Error(e.message ?: "Unknown error")
            }
        }

    }

}