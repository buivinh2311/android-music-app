package com.example.core_database.datasource.user

import com.example.core_database.entity.user.UserEntity

interface UserLocalDataSource {
    suspend fun getUserById(userId: Int): UserEntity?
    suspend fun insert(user: UserEntity): Boolean
    suspend fun update(user: UserEntity)
    suspend fun delete(user: UserEntity)
}