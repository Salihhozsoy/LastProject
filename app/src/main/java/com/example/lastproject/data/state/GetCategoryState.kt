package com.example.lastproject.data.state

import com.example.lastproject.data.locale.CategoryEntity

sealed class GetCategoryState{

    object Idle:GetCategoryState()
    object Empty: GetCategoryState()
    class Success(val categories: List<CategoryEntity>):GetCategoryState()
    class Error(val throwable: Throwable):GetCategoryState()
}
