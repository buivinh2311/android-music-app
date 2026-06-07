package com.example.core_domain.repository

import com.example.core_model.User

interface UserRepository {
    suspend fun getUserById(userId: Int): User?
    suspend fun register(user: User): Boolean
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}