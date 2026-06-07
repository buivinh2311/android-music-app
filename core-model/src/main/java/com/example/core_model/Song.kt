package com.example.core_model

data class Song(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val sourceUrl: String,
    val artworkUrl: String,
    val duration: Int,
    val favorite: Int,
    val counter: Int,
    val replay: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Song

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}