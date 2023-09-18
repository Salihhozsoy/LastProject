package com.example.lastproject.data.repository

import com.example.lastproject.data.model.Photo

interface PhotoRepository {
    suspend fun getAllPhotos(query:String):List<Photo>

    suspend fun getPhotoById(id:Int) :Photo
}