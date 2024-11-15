package com.example.shopease.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.shopease.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userUseCase: UserUseCase):ViewModel() {

    fun getStart(): Boolean {
        return userUseCase.getStart()
    }

}