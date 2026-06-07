package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserDao
import com.example.core_database.datasource.user.UserLocalDataSource
import com.example.core_database.entity.user.UserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
): UserLocalDataSource {
    override suspend fun getUserById(userId: Int): UserEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun update(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(user: UserEntity) {
        TODO("Not yet implemented")
    }
}