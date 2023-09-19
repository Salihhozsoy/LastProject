package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.entity.UserEntity

interface UserRepository {

    suspend fun getProfileById(id: Int): UserEntity

    suspend fun updateProfile(userEntity: UserEntity)
}