package com.example.core_model

data class Song(
    val id: String,
    val title: String,
    val album: String? = null,
    val artist: String,
    val sourceUrl: String,
    val artworkUrl: String,
    val duration: Int,
    val favorite: Int,
    val counter: Int,
    val replay: Int
)