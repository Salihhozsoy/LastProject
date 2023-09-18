package com.example.lastproject.ui.photodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.model.Photo
import com.example.lastproject.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailFragmentViewModel @Inject constructor(private val photoRepository: PhotoRepository):ViewModel() {

    private val _photoDetailState: MutableStateFlow<Photo?> = MutableStateFlow(null)
    val photoDetailState: StateFlow<Photo?> = _photoDetailState

    fun getPhotoById(id: Int) {
        viewModelScope.launch {
            runCatching {
                _photoDetailState.value = photoRepository.getPhotoById(id)
            }
        }
    }
}