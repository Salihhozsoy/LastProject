package com.example.lastproject.data.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lastproject.data.locale.entity.TakenPhotoEntity
import retrofit2.http.GET

@Dao
interface TakenPhotoDao {
    @Insert
    suspend fun insert(takenPhotoEntity: TakenPhotoEntity)
    @Query("select * from TakenPhotoEntity")
    suspend fun getAllTakenPhotos() :List<TakenPhotoEntity>

}