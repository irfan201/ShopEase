package com.example.shopease.data.source.remote

import com.example.shopease.data.model.OrderHistoryDetailDto
import com.example.shopease.data.model.OrderDto
import com.example.shopease.data.model.OrderHistoryDto
import com.example.shopease.data.model.OrderRequest
import com.example.shopease.data.model.ProductCategoryDto
import com.example.shopease.data.model.ProductDetailDto
import com.example.shopease.data.model.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(@Query("search") search: String? = null): ProductDto

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDetailDto

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): ProductCategoryDto


    @POST("order/snap")
    suspend fun orderProduct(
        @Header("x-user-id") idUser: Int = 1,
        @Header("content-type") contentType: String = "application/json",
        @Body orderRequest: OrderRequest
    ): OrderDto

    @GET("orders")
    suspend fun getOrderHistory(
        @Header("x-user-id") idUser: Int = 1,
        @Query("search") email: String,
        @Query("orderPaymentStatus") orderPaymentStatus: String = "settlement",
    ): OrderHistoryDto

    @GET("order/{id}")
    suspend fun getOrderHistoryDetail(
        @Header("x-user-id") idUser: Int = 1,
        @Path("id") id: String
    ): OrderHistoryDetailDto

}