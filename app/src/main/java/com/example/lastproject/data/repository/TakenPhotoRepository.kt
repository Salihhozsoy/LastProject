package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.entity.TakenPhotoEntity

interface TakenPhotoRepository {
    suspend fun insert(takenPhotoEntity: TakenPhotoEntity)

    suspend fun getAllTakenPhotos() : List<TakenPhotoEntity>
}