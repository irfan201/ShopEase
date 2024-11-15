package com.example.shopease.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.shopease.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userUseCase: UserUseCase):ViewModel() {

    fun getCurrentUser() = userUseCase.getCurrentUser()

    suspend fun logout(){
        userUseCase.logout()

    }

}