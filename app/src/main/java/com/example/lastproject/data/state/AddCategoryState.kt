package com.example.lastproject.data.state

sealed class AddCategoryState{
    object Idle:AddCategoryState()
    object Result:AddCategoryState()
    object CategoryAlready:AddCategoryState()
    object Empty:AddCategoryState()
    class Error(val throwable: Throwable?=null):AddCategoryState()
}
