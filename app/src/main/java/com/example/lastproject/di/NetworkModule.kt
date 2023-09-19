package com.example.lastproject.di

import com.example.lastproject.Constants.BASE_API_URL
import com.example.lastproject.data.service.PhotoService
import com.example.lastproject.util.ConnectivityObserver
import com.example.lastproject.util.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_API_URL).build()

    @Provides
    fun provideService(retrofit: Retrofit) : PhotoService =retrofit.create(PhotoService::class.java)

    @Provides
    @Singleton
    fun provideConnectivityObserver(connectivityObserver: NetworkConnectivityObserver): ConnectivityObserver = connectivityObserver
}