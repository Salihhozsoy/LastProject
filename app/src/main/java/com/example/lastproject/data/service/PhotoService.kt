package com.example.lastproject.data.service

import com.example.lastproject.Constants.API_KEY
import com.example.lastproject.data.model.Photo
import com.example.lastproject.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {

    @Headers(
        "Authorization: $API_KEY"
    )

    @GET("v1/search")
    suspend fun getAllPhotos(@Query("query")queryText:String) : Response<List<Photo>>
    @GET("v1/photos/{id}")
    suspend fun getPhotoById(@Path("id") id:Int) : Photo
}