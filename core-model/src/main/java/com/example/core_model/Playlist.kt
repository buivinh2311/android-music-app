package com.example.core_model

data class Playlist(
    val id: Int,
    val name: String,
    val artwork: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val size: Int? = 0
)
