package com.example.lastproject.data.repository

import com.example.lastproject.data.model.Photo
import com.example.lastproject.data.service.PhotoService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val photoService: PhotoService):PhotoRepository {
    override suspend fun getAllPhotos(query: String): List<Photo> {
       return photoService.getAllPhotos(query).photos
    }

    override suspend fun getPhotoById(id: Int): Photo {
       return photoService.getPhotoById(id)
    }
}