package com.example.infrastructure.mapper.local

import com.example.core_database.entity.user.UserEntity
import com.example.core_model.User

fun UserEntity.toModel(): User {
    return User(
        id = this.id,
        username = this.username,
        email = this.email,
        avatar = this.avatar
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        username = this.username,
        email = this.email,
        avatar = this.avatar
    )
}