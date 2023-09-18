package com.example.lastproject.di

import com.example.lastproject.Constants.BASE_API_URL
import com.example.lastproject.data.service.PhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_API_URL).build()

    @Provides
    fun provideService(retrofit: Retrofit) : PhotoService =retrofit.create(PhotoService::class.java)
}