package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.FavouriteDao
import com.example.lastproject.data.locale.FavouriteEntity
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(private val favouriteDao: FavouriteDao):FavouriteRepository {
    override suspend fun insert(favouriteEntity: FavouriteEntity) {
       favouriteDao.insert(favouriteEntity)
    }

    override suspend fun getAllFavourites(userId:Int): List<FavouriteEntity> {
       return favouriteDao.getAllFavourites(userId)
    }

    override suspend fun getFavouriteById(id: Int): FavouriteEntity? {
        return favouriteDao.getFavouriteById(id)
    }

    override suspend fun delete(favouriteEntity: FavouriteEntity) {
        favouriteDao.delete(favouriteEntity)
    }
}