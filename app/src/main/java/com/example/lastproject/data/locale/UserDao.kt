package com.example.lastproject.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("select * from UserEntity where email=:email and password =:password")
    suspend fun getUserControlByEmail(email: String, password: String): UserEntity?

    @Query("select * from UserEntity where username=:username and password =:password")
    suspend fun getUserControlByUsername(username: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Query("select * from UserEntity where email=:email")
     suspend fun getUserByEmail(email: String): UserEntity?

    @Query("select * from UserEntity where username=:username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("select * from UserEntity where id=:id")
    suspend fun getProfileById(id:Int) :UserEntity

    @Update
    suspend fun updateProfile(user: UserEntity)
}