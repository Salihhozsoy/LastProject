package com.example.lastproject.data.locale

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


@Entity(foreignKeys = arrayOf(
    ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = CASCADE
    )
))

data class FavouriteEntity(
    @PrimaryKey val id:Int,
    val favouriteImageUrl:String,
    val userId:Int
)
