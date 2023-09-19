package com.example.lastproject.ui.addcategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastproject.data.locale.entity.CategoryEntity
import com.example.lastproject.data.repository.CategoryRepository
import com.example.lastproject.data.state.AddCategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {


    private val _addCategoryState: MutableStateFlow<AddCategoryState> = MutableStateFlow(AddCategoryState.Idle)
    val addCategoryState: StateFlow<AddCategoryState> = _addCategoryState
    fun addCategory(categoryName: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                if (categoryName.isNotEmpty()) {
                    if (categoryRepository.getCategoryByName(categoryName) == AddCategoryState.Result) {
                        val category = CategoryEntity(categoryName = categoryName)
                        categoryRepository.insert(category)
                        _addCategoryState.value = AddCategoryState.Result

                    } else {
                        _addCategoryState.value = AddCategoryState.CategoryAlready
                    }
                } else {
                        _addCategoryState.value = AddCategoryState.Empty
                }
            }.onFailure {
                _addCategoryState.value = AddCategoryState.Error(it)
            }
        }

    }
}