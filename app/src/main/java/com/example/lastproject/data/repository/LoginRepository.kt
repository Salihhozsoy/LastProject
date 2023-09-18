package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.UserEntity
import com.example.lastproject.data.state.LoginState

interface LoginRepository {

    suspend fun loginByEmail(email:String,password:String) :UserEntity?
    suspend fun loginByUsername(username:String,password:String) :UserEntity?
}