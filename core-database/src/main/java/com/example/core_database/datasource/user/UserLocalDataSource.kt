package com.example.core_database.datasource.user

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.core_database.entity.user.UserEntity

interface UserLocalDataSource {
    suspend fun getUserById(userId: Int): UserEntity?
    suspend fun insert(user: UserEntity)
    suspend fun update(user: UserEntity)
    suspend fun delete(user: UserEntity)
}