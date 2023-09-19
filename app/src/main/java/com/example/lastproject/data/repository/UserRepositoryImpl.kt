package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.dao.UserDao
import com.example.lastproject.data.locale.entity.UserEntity
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao):UserRepository {
    override suspend fun getProfileById(id: Int): UserEntity {
        return userDao.getProfileById(id)
    }

    override suspend fun updateProfile(userEntity: UserEntity)  {
          userDao.updateProfile(userEntity)
    }
}