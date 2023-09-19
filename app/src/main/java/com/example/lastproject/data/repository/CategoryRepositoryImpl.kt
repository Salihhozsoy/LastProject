package com.example.lastproject.data.repository

import com.example.lastproject.data.locale.dao.CategoryDao
import com.example.lastproject.data.locale.entity.CategoryEntity
import com.example.lastproject.data.state.AddCategoryState
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val categoryDao: CategoryDao):CategoryRepository {
    override suspend fun insert(category: CategoryEntity) {
       categoryDao.insert(category)
    }

    override suspend fun getCategoryByName(categoryName: String): AddCategoryState {
        return if (categoryDao.getCategoryByName(categoryName) == null) AddCategoryState.Result
        else AddCategoryState.CategoryAlready
    }

    override suspend fun getCategories(): List<CategoryEntity> {
        return categoryDao.getCategories()
    }
}