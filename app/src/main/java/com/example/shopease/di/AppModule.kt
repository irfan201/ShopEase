package com.example.shopease.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.shopease.data.source.local.LocalDataSource
import com.example.shopease.data.source.local.LocalDataSourceImpl
import com.example.shopease.data.source.local.preference.UserDataStore
import com.example.shopease.data.source.local.preference.dataStore
import com.example.shopease.data.source.local.room.CartDao
import com.example.shopease.data.source.local.room.FavoriteDao
import com.example.shopease.data.source.local.room.ProductDatabase
import com.example.shopease.data.source.remote.ApiService
import com.example.shopease.data.source.remote.NetworkConfig
import com.example.shopease.data.source.remote.RemoteDataSource
import com.example.shopease.data.source.remote.RemoteDataSourceImpl
import com.example.shopease.domain.repository.ProductRepository
import com.example.shopease.domain.repository.ProductRepositoryImpl
import com.example.shopease.domain.repository.UserRepository
import com.example.shopease.domain.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return NetworkConfig().getService()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService,
        firebaseAuth: FirebaseAuth
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiService, firebaseAuth)

    }

    @Provides
    @Singleton
    fun provideProductRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun localDataSource(
        userDataStore: UserDataStore,
        favoriteDao: FavoriteDao,
        cartDao: CartDao
    ): LocalDataSource {
        return LocalDataSourceImpl(userDataStore, favoriteDao, cartDao)
    }

    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return ProductDatabase.getInstance(context)

    }

    @Provides
    @Singleton
    fun provideCartDao(productDatabase: ProductDatabase) = productDatabase.cartDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(productDatabase: ProductDatabase) = productDatabase.favoriteDao()


}