package com.example.lastproject.data.locale

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val username:String?,
    val email:String?,
    val password:String?
)