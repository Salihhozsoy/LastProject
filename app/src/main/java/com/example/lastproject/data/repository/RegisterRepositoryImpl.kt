package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.UserDao
import com.example.lastproject.data.locale.UserEntity
import com.example.lastproject.data.state.RegisterState
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val userDao: UserDao):RegisterRepository {
    override suspend fun insert(user: UserEntity) {
        userDao.insert(user)
    }

    override suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getUserByUsername(username: String): UserEntity? {
      return userDao.getUserByUsername(username)
    }
}