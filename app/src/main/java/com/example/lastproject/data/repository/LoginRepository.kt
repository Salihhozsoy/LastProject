package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.entity.UserEntity

interface LoginRepository {

    suspend fun loginByEmail(email:String,password:String) : UserEntity?
    suspend fun loginByUsername(username:String,password:String) : UserEntity?
}