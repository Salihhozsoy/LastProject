package com.example.lastproject.data.state

import com.example.lastproject.data.locale.entity.TakenPhotoEntity
import com.example.lastproject.data.model.Photo

sealed class TakenPhotosListState{
    object Idle:TakenPhotosListState()
    object Loading:TakenPhotosListState()
    object Empty:TakenPhotosListState()
    class Result(val takenPhotos:List<TakenPhotoEntity>):TakenPhotosListState()
    object Error:TakenPhotosListState()
}
