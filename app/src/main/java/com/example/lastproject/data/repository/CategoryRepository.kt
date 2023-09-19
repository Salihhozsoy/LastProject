package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.entity.CategoryEntity
import com.example.lastproject.data.state.AddCategoryState

interface CategoryRepository {

    suspend fun insert(category: CategoryEntity)
    suspend fun getCategoryByName(categoryName:String) : AddCategoryState
    suspend fun getCategories() :List<CategoryEntity>
}