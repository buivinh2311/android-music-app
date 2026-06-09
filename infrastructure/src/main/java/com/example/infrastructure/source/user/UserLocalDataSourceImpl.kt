package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserDao
import com.example.core_database.datasource.user.UserLocalDataSource
import com.example.core_database.entity.user.UserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
): UserLocalDataSource {
    override suspend fun getUserById(userId: Int): UserEntity? {
        return userDao.getUserById(userId)
    }

    override suspend fun insert(user: UserEntity): Boolean {
        return userDao.insert(user) > 0
    }

    override suspend fun update(user: UserEntity) {
        userDao.update(user)
    }

    override suspend fun delete(user: UserEntity) {
        userDao.delete(user)
    }
}