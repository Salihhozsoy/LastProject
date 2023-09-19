package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.dao.UserDao
import com.example.lastproject.data.locale.entity.UserEntity
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val userDao: UserDao) : LoginRepository {
    override suspend fun loginByEmail(email: String, password: String): UserEntity? {
        userDao.getUserControlByEmail(email, password)?.let {
            return it
        } ?: run {
            return null
        }
    }

    override suspend fun loginByUsername(username: String, password: String): UserEntity? {
        userDao.getUserControlByUsername(username, password)?.let {
            return it
        } ?: run {
            return null
        }
    }
}