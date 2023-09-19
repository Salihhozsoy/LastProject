package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.entity.FavouriteEntity

interface FavouriteRepository {
    suspend fun insert(favouriteEntity: FavouriteEntity)
    suspend fun getAllFavourites(userId:Int) : List<FavouriteEntity>

    suspend fun getFavouriteById(id:Int) : FavouriteEntity?
    suspend fun delete(favouriteEntity: FavouriteEntity)
}