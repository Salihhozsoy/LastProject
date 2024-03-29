package com.example.lastproject.di

import android.content.Context
import androidx.room.Room
import com.example.lastproject.Constants.DATABASE_NAME
import com.example.lastproject.data.locale.AppDatabase
import com.example.lastproject.data.locale.dao.CategoryDao
import com.example.lastproject.data.locale.dao.FavouriteDao
import com.example.lastproject.data.locale.dao.PhotoDao
import com.example.lastproject.data.locale.dao.TakenPhotoDao
import com.example.lastproject.data.locale.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase) : UserDao {
        return appDatabase.userDao()
    }
    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase) : CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun providePhotoDao(appDatabase: AppDatabase) : PhotoDao {
        return appDatabase.photoDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(appDatabase: AppDatabase) : FavouriteDao {
        return appDatabase.favouriteDao()
    }
    @Provides
    @Singleton
    fun provideTakenPhotoDao(appDatabase: AppDatabase) : TakenPhotoDao{
        return appDatabase.takenPhotoDao()
    }


}