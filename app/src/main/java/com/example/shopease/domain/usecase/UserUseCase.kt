package com.example.shopease.domain.usecase

import androidx.credentials.GetCredentialResponse
import com.example.shopease.domain.model.User
import com.example.shopease.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): User? {
        return userRepository.signInWithGoogle(credentialResponse)
    }

    fun getCurrentUser(): User? {
        return userRepository.getCurrentUser()
    }

    suspend fun putToken(token: String) {
        userRepository.putToken(token)
    }

    fun getToken(): String? {
        return userRepository.getToken()
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




}