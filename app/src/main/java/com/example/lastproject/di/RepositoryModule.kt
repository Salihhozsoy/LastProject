package com.example.lastproject.di

import com.example.lastproject.data.repository.CategoryRepository
import com.example.lastproject.data.repository.CategoryRepositoryImpl
import com.example.lastproject.data.repository.LoginRepository
import com.example.lastproject.data.repository.LoginRepositoryImpl
import com.example.lastproject.data.repository.RegisterRepository
import com.example.lastproject.data.repository.RegisterRepositoryImpl
import com.example.lastproject.data.repository.UserRepository
import com.example.lastproject.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository =
        loginRepositoryImpl

    @Provides
    @Singleton
    fun provideRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository =
        registerRepositoryImpl

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl) :CategoryRepository =categoryRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl) :UserRepository =userRepositoryImpl
}