package com.example.shopease.domain.repository

import androidx.credentials.GetCredentialResponse
import com.example.shopease.data.source.local.LocalDataSource
import com.example.shopease.data.source.remote.RemoteDataSource
import com.example.shopease.domain.model.User
import com.example.shopease.utils.DataMapper.toUser
import javax.inject.Inject

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

    override suspend fun saveLogin(isLogin: Boolean) {
        localDataSource.saveLogin(isLogin)
    }

    override fun getLogin(): Boolean {
        return localDataSource.getLogin()
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

    override fun clearCategory() {
        localDataSource.clearCategory()
    }

}