package com.example.lastproject.ui.dashboard

import android.graphics.Bitmap
import android.media.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.entity.CategoryEntity
import com.example.lastproject.data.locale.entity.TakenPhotoEntity
import com.example.lastproject.data.repository.CategoryRepository
import com.example.lastproject.data.repository.PhotoRepository
import com.example.lastproject.data.repository.TakenPhotoRepository
import com.example.lastproject.data.state.GetCategoryState
import com.example.lastproject.data.state.PhotoListState
import com.example.lastproject.data.state.TakenPhotosListState
import com.example.lastproject.util.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val categoryRepository: CategoryRepository,
    private val connectivityObserver: ConnectivityObserver,
    private val takenPhotoRepository: TakenPhotoRepository
) : ViewModel() {

    private val _photoListState: MutableStateFlow<PhotoListState> =
        MutableStateFlow(PhotoListState.Idle)
    val photoListState: StateFlow<PhotoListState> = _photoListState

    private val _takenPhotosListState: MutableStateFlow<TakenPhotosListState> =
        MutableStateFlow(TakenPhotosListState.Idle)
    val takenPhotosListState: StateFlow<TakenPhotosListState> = _takenPhotosListState

    private var _getCategoryState: MutableStateFlow<GetCategoryState> =
        MutableStateFlow(GetCategoryState.Idle)
    var getCategoryState: StateFlow<GetCategoryState> = _getCategoryState

    private val selectedCategory: MutableStateFlow<CategoryEntity?> = MutableStateFlow(null)

    private val _connectivityStatus: MutableStateFlow<ConnectivityObserver.NetworkStatus> =
        MutableStateFlow(ConnectivityObserver.NetworkStatus.Unavailable)
    val connectivityStatus = _connectivityStatus.asStateFlow()

    init {
        observeConnectivity()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collect {
                _connectivityStatus.emit(it)
            }
        }
    }
    private fun getAllPhotos(query: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                _photoListState.value = PhotoListState.Loading
                val photos = photoRepository.getAllPhotos(query)
                if (photos.isEmpty()) _photoListState.value = PhotoListState.Empty
                else _photoListState.value = PhotoListState.Result(photos)
            }.onFailure {
                _photoListState.value = PhotoListState.Error
                it.printStackTrace()
            }
        }
    }
     fun getAllTakenPhotos(){
        viewModelScope.launch {
            kotlin.runCatching {
                _takenPhotosListState.value = TakenPhotosListState.Loading
                val takenPhotos = takenPhotoRepository.getAllTakenPhotos()
                if (takenPhotos.isEmpty()) _takenPhotosListState.value = TakenPhotosListState.Empty
                else _takenPhotosListState.value = TakenPhotosListState.Result(takenPhotos)
            }.onFailure {
                _takenPhotosListState.value = TakenPhotosListState.Error
                it.printStackTrace()
            }
            }
        }


    fun getAllCategory() {
        viewModelScope.launch {
            kotlin.runCatching {
                val categories = categoryRepository.getCategories()
                _getCategoryState.value =
                    if (categories.isEmpty()) GetCategoryState.Empty else GetCategoryState.Success(
                        categories
                    )
            }.onFailure {
                _getCategoryState.value = GetCategoryState.Error(it)
            }
        }
    }

    fun categorySelected(position: Int) {
        viewModelScope.launch {
            if (_getCategoryState.value is GetCategoryState.Success) {
                val category =
                    (_getCategoryState.value as GetCategoryState.Success).categories[position]
                selectedCategory.value = category
                getAllPhotos(category.categoryName.toString())
            }
        }
    }
    fun saveToDb(photo:ByteArray){
        viewModelScope.launch {
            kotlin.runCatching {
                val taken= TakenPhotoEntity(image = photo)
                takenPhotoRepository.insert(taken)
            }
        }
    }
}