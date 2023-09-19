package com.example.lastproject.ui.photodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.entity.FavouriteEntity
import com.example.lastproject.data.model.Photo
import com.example.lastproject.data.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailFragmentViewModel @Inject constructor(private val favouriteRepository: FavouriteRepository) :
    ViewModel() {

    private val _isFavourite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite

    private val _message:MutableSharedFlow<Boolean> = MutableSharedFlow()
    val message:SharedFlow<Boolean> = _message

    fun checkFavourite(photo: Photo) {
        viewModelScope.launch {
            kotlin.runCatching {
                favouriteRepository.getFavouriteById(photo.id)?.let {
                    _isFavourite.value = true
                }?: kotlin.run {
                    _isFavourite.value=false
                }
            }
        }
    }

    fun insertOrDelete(photo: Photo,userId:Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                if (_isFavourite.value) {
                    val favourite = favouriteRepository.getFavouriteById(photo.id)
                    favourite?.let {
                        favouriteRepository.delete(it)
                        _message.emit(false)
                    }

                } else {
                    favouriteRepository.insert(
                        FavouriteEntity(photo.id, photo.src.landscape,userId)
                    )
                    _message.emit(true)
                }
                checkFavourite(photo)
            }
        }
    }
}