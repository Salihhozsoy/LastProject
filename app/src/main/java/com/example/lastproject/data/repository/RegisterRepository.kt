package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.UserEntity
import com.example.lastproject.data.state.RegisterState

interface RegisterRepository {

    suspend fun insert(user: UserEntity)

    suspend fun getUserByEmail(email:String) : UserEntity?

    suspend fun getUserByUsername(username:String) : UserEntity?
}