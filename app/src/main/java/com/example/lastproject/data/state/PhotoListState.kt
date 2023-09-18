package com.example.lastproject.data.state

import com.example.lastproject.data.model.Photo

sealed class PhotoListState{
    object Idle:PhotoListState()
    object Loading:PhotoListState()
    object Empty:PhotoListState()
    class Result(val photos:List<Photo>):PhotoListState()
    object Error:PhotoListState()
}

