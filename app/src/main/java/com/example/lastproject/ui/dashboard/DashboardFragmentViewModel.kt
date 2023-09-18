package com.example.lastproject.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.CategoryEntity
import com.example.lastproject.data.repository.CategoryRepository
import com.example.lastproject.data.repository.PhotoRepository
import com.example.lastproject.data.state.GetCategoryState
import com.example.lastproject.data.state.PhotoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(private val photoRepository: PhotoRepository,private val categoryRepository: CategoryRepository):ViewModel() {

    private val _photoListState: MutableStateFlow<PhotoListState> = MutableStateFlow(PhotoListState.Idle)
    val photoListState: StateFlow<PhotoListState> =_photoListState

    private var _getCategoryState: MutableStateFlow<GetCategoryState> = MutableStateFlow(GetCategoryState.Idle)
    var getCategoryState: StateFlow<GetCategoryState> = _getCategoryState

    private val selectedCategory: MutableStateFlow<CategoryEntity?> = MutableStateFlow(null)
    private fun getAllPhotos(query:String){
        viewModelScope.launch {
            kotlin.runCatching {
                _photoListState.value =PhotoListState.Loading
                val photos = photoRepository.getAllPhotos(query)
                if(photos.isEmpty()) _photoListState.value =PhotoListState.Empty
                else _photoListState.value =PhotoListState.Result(photos)
            }.onFailure {
                _photoListState.value =PhotoListState.Error
                it.printStackTrace()
            }
        }
    }

    fun getAllCategory(){
        viewModelScope.launch {
            kotlin.runCatching {
                 val categories = categoryRepository.getCategories()
                _getCategoryState.value =  if(categories.isEmpty()) GetCategoryState.Empty else GetCategoryState.Success(categories)
            }.onFailure {
                _getCategoryState.value = GetCategoryState.Error(it)
            }
        }
    }

    fun categorySelected(position: Int) {
        viewModelScope.launch {
            if (_getCategoryState.value is GetCategoryState.Success) {
                val category = (_getCategoryState.value as GetCategoryState.Success).categories[position]
                selectedCategory.value = category
                getAllPhotos(category.categoryName.toString())
            }
        }
    }
}