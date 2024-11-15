package com.example.shopease.domain.repository

import androidx.credentials.GetCredentialResponse
import com.example.shopease.data.source.local.LocalDataSource
import com.example.shopease.data.source.remote.RemoteDataSource
import com.example.shopease.domain.model.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

interface UserRepository {
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): User?
    fun getCurrentUser(): User?
    suspend fun putToken(token: String)
    fun getToken(): String?
    suspend fun logout()
    suspend fun saveCategory(category: String)
    fun getCategory(): String?
    suspend fun saveStart(start: Boolean)
    fun getStart(): Boolean
}

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : UserRepository {
    override suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): User? {
        return remoteDataSource.signInWithGoogle(credentialResponse)?.toUser()
    }

    override fun getCurrentUser(): User? {
        return remoteDataSource.getCurrentUser()?.toUser()
    }

    override suspend fun putToken(token: String) {
        localDataSource.putToken(token)
    }

    override fun getToken(): String? {
        return localDataSource.getToken()
    }

    override suspend fun logout() {
        localDataSource.logout()
    }

    override suspend fun saveCategory(category: String) {
        localDataSource.saveCategory(category)
    }

    override fun getCategory(): String? {
        return localDataSource.getCategory()
    }

    override suspend fun saveStart(start: Boolean) {
        localDataSource.saveStart(start)
    }

    override fun getStart(): Boolean {
        return localDataSource.getStart()
    }

    private fun FirebaseUser.toUser(): User {
        return User(
            uid = uid,
            name = displayName ?: "",
            email = email ?: "",
            photoUrl = photoUrl.toString()
        )
    }


}