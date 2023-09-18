package com.example.lastproject.data.locale

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteDao {

    @Insert
    suspend fun insert(favouriteEntity: FavouriteEntity)

    @Delete
    suspend fun delete(favouriteEntity: FavouriteEntity)

    @Query("select * from FavouriteEntity where userId=:userId")
    suspend fun getAllFavourites(userId:Int): List<FavouriteEntity>

    @Query("select * from FavouriteEntity where id=:id")
    suspend fun getFavouriteById(id: Int): FavouriteEntity?
}