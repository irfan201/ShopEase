package com.example.shopease.data.source.remote

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.example.shopease.data.model.OrderDto
import com.example.shopease.data.model.OrderHistoryDetailDto
import com.example.shopease.data.model.OrderHistoryDto
import com.example.shopease.data.model.OrderRequest
import com.example.shopease.data.model.ProductCategoryDto
import com.example.shopease.data.model.ProductDetailDto
import com.example.shopease.data.model.ProductDto
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getProducts(): ProductDto
    suspend fun getDetailProduct(id: Int): ProductDetailDto
    suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): FirebaseUser?
    fun getCurrentUser(): FirebaseUser?
    suspend fun getProductsByCategory(category: String): ProductCategoryDto
    suspend fun getProductbBySearch(search: String): ProductDto
    suspend fun orderProduct(orderRequest: OrderRequest): OrderDto
    suspend fun getOrderHistory(email: String): OrderHistoryDto
    suspend fun getOrderHistoryDetail(id: String): OrderHistoryDetailDto
}

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val firebaseAuth: FirebaseAuth
) :
    RemoteDataSource {
    override suspend fun getProducts(): ProductDto {
        return apiService.getProducts()
    }

    override suspend fun getDetailProduct(id: Int): ProductDetailDto {
        return apiService.getProductById(id)
    }

    override suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): FirebaseUser? {
        val googleIdToken = getGoogleIdTokenCredential(credentialResponse)
        val credential: AuthCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        Log.d("RemoteDataSourceImpl", "signInWithGoogle: $credential")
        return firebaseAuth.signInWithCredential(credential).await().user
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun getProductsByCategory(category: String): ProductCategoryDto {
        return apiService.getProductsByCategory(category)
    }

    override suspend fun getProductbBySearch(search: String): ProductDto {
        return apiService.getProducts(search)
    }

    override suspend fun orderProduct(orderRequest: OrderRequest): OrderDto {
        return apiService.orderProduct(orderRequest = orderRequest)
    }

    override suspend fun getOrderHistory(email: String): OrderHistoryDto {
        return apiService.getOrderHistory(email = email)

    }

    override suspend fun getOrderHistoryDetail(id: String): OrderHistoryDetailDto {
        return apiService.getOrderHistoryDetail(id = id)
    }

    private fun getGoogleIdTokenCredential(credentialResponse: GetCredentialResponse): String? {
        return when (val credential = credentialResponse.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    googleIdTokenCredential.idToken
                } else {
                    null
                }
            }

            else -> null
        }
    }

}