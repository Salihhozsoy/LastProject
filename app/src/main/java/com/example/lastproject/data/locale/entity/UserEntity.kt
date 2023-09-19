package com.example.lastproject.data.locale.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val username:String?,
    val email:String?,
    val password:String?,
    val profileImageUrl:String?=null
)