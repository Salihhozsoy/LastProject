package com.example.lastproject.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lastproject.data.locale.dao.CategoryDao
import com.example.lastproject.data.locale.dao.FavouriteDao
import com.example.lastproject.data.locale.dao.PhotoDao
import com.example.lastproject.data.locale.dao.UserDao
import com.example.lastproject.data.locale.entity.CategoryEntity
import com.example.lastproject.data.locale.entity.FavouriteEntity
import com.example.lastproject.data.locale.entity.UserEntity

@Database(entities = [UserEntity::class , CategoryEntity::class, FavouriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun photoDao() : PhotoDao
    abstract fun categoryDao(): CategoryDao
    abstract fun favouriteDao(): FavouriteDao
}