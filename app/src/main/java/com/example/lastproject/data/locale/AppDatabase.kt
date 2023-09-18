package com.example.lastproject.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class , CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun photoDao() :PhotoDao
    abstract fun categoryDao():CategoryDao
}