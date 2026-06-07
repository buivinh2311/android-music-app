package com.example.core_domain.manager

import com.example.core_model.User

interface UserManager {
    fun isLoggedIn(): Boolean
    fun getCurrentUserId(): Int
    fun getCurrentUser(): User?
    fun login(user: User)
    fun logout()
}