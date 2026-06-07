package com.example.infrastructure.repository

import com.example.core_database.datasource.user.UserLocalDataSource
import com.example.core_domain.repository.UserRepository
import com.example.core_model.User
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
): UserRepository {
    override suspend fun getUserById(userId: Int): User? {
        return userLocalDataSource.getUserById(userId)?.toModel()
    }

    override suspend fun register(user: User): Boolean {
        return userLocalDataSource.insert(user.toEntity())
    }

    override suspend fun updateUser(user: User) {
        userLocalDataSource.update(user.toEntity())
    }

    override suspend fun deleteUser(user: User) {
        userLocalDataSource.delete(user.toEntity())
    }
}