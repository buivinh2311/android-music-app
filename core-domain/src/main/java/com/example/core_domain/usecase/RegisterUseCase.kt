package com.example.core_domain.usecase

import com.example.core_domain.repository.UserRepository
import com.example.core_model.User
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Boolean {
        val userModel = repository.getUserById(user.id) ?: return repository.register(user)
        return false
    }
}