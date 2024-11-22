package com.example.shopease.presentation.onBoarding

import androidx.lifecycle.ViewModel
import com.example.shopease.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    suspend fun saveStart(start: Boolean) {
        userUseCase.saveStart(start)
    }

    fun getLogin():Boolean{
        return userUseCase.getLogin()
    }
}