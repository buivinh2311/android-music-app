package com.example.core_model

data class User(
    val id: Int,
    val username: String,
    val email: String? = null,
    val avatar: String? = null
)
