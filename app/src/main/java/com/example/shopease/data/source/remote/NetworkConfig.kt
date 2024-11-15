package com.example.shopease.data.source.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig {

    private fun Interceptor(): Interceptor {
        return Interceptor {
            val chain = it.request()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val token = "4h2a>UB%_DO1)QwaP|%[xHgp\$:IRp4"
            val requestHeaders = chain.newBuilder()
                .addHeader("accept", "/")
                .addHeader("x-secret-app", token)
                .build()
            it.proceed(requestHeaders)
        }
    }

    private fun getIntraCeptor(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor()).build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://phincon-academy-api.onrender.com/phincon/api/")
            .client(getIntraCeptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofit().create(ApiService::class.java)

}