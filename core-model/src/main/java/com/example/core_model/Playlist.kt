package com.example.core_model

data class Playlist(
    val id: Int,
    val name: String,
    val artwork: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val songs: List<Song> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Playlist

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
