package com.example.lastproject.data.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lastproject.data.locale.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: CategoryEntity)

    @Query("select * from CategoryEntity")
    suspend fun getCategories() :List<CategoryEntity>

    @Query("select * from CategoryEntity where categoryName=:name")
    suspend fun getCategoryByName(name: String): CategoryEntity?
}