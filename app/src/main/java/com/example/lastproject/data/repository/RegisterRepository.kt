package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.entity.UserEntity

interface RegisterRepository {

    suspend fun insert(user: UserEntity)

    suspend fun getUserByEmail(email:String) : UserEntity?

    suspend fun getUserByUsername(username:String) : UserEntity?
}