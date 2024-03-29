package com.example.lastproject.data.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lastproject.data.locale.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity WHERE email=:email AND password =:password")
    suspend fun getUserControlByEmail(email: String, password: String): UserEntity?

    @Query("select * from UserEntity where username=:username and password =:password")
    suspend fun getUserControlByUsername(username: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE email=:email")
     suspend fun getUserByEmail(email: String): UserEntity?

    @Query("select * from UserEntity where username=:username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("select * from UserEntity where id=:id")
    suspend fun getProfileById(id:Int) : UserEntity

    @Update
    suspend fun updateProfile(user: UserEntity)
}