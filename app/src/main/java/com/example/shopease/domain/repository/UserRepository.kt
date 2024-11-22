package com.example.shopease.domain.repository

import androidx.credentials.GetCredentialResponse
import com.example.shopease.domain.model.User

interface UserRepository {
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): User?
    fun getCurrentUser(): User?
    suspend fun saveLogin(isLogin: Boolean)
    fun getLogin(): Boolean
    suspend fun logout()
    suspend fun saveCategory(category: String)
    fun getCategory(): String?
    suspend fun saveStart(start: Boolean)
    fun getStart(): Boolean
    fun clearCategory()
}




