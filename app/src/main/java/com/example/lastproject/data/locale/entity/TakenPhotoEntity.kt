package com.example.lastproject.data.locale.entity

import android.graphics.Bitmap
import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TakenPhotoEntity(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val image:ByteArray
)
