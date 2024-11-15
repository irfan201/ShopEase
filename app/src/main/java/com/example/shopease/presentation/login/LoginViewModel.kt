package com.example.shopease.presentation.login

import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopease.domain.model.UserState
import com.example.shopease.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase):ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState

    fun signInWithGoogle(credentialResponse: GetCredentialResponse) {
        viewModelScope.launch {
            try {
                val user = userUseCase.signInWithGoogle(credentialResponse)
                _userState.value = UserState.Success(user)

            }catch (e:Exception){
                _userState.value = UserState.Error(e.message ?: "Unknown error")

            }
        }
    }

    suspend fun putToken(token: String) {
        userUseCase.putToken(token)
    }

    fun getLogin():String?{
        return userUseCase.getToken()
    }
}