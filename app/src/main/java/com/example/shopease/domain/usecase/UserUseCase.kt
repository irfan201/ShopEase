package com.example.shopease.domain.usecase

import androidx.credentials.GetCredentialResponse
import com.example.shopease.domain.model.User
import com.example.shopease.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): User? {
        return userRepository.signInWithGoogle(credentialResponse)
    }

    fun getCurrentUser(): User? {
        return userRepository.getCurrentUser()
    }

    suspend fun saveLogin(isLogin: Boolean){
        userRepository.saveLogin(isLogin)
    }

    fun getLogin(): Boolean {
        return userRepository.getLogin()
    }

    suspend fun logout() {
        userRepository.logout()
    }

    suspend fun saveCategory(category: String) {
        userRepository.saveCategory(category)
    }

    fun getCategory(): String? {
        return userRepository.getCategory()
    }

    suspend fun saveStart(start: Boolean) {
        userRepository.saveStart(start)
    }

    fun getStart(): Boolean {
        return userRepository.getStart()
    }

    fun clearCategory(){
        userRepository.clearCategory()
    }



}