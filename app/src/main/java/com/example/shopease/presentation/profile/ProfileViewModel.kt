package com.example.shopease.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userUseCase: UserUseCase):ViewModel() {

    fun getCurrentUser() = userUseCase.getCurrentUser()

     fun logout(){
        viewModelScope.launch {
            userUseCase.logout()
        }
    }

}