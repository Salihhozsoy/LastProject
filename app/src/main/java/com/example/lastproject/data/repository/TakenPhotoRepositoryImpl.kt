package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.dao.TakenPhotoDao
import com.example.lastproject.data.locale.entity.TakenPhotoEntity
import javax.inject.Inject

class TakenPhotoRepositoryImpl @Inject constructor(private val takenPhotoDao: TakenPhotoDao): TakenPhotoRepository {
    override suspend fun insert(takenPhotoEntity: TakenPhotoEntity) {
       takenPhotoDao.insert(takenPhotoEntity)
    }

    override suspend fun getAllTakenPhotos(): List<TakenPhotoEntity> {
       return takenPhotoDao.getAllTakenPhotos()
    }
}