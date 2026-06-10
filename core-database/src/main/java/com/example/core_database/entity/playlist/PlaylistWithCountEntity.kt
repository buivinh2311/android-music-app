package com.example.core_database.entity.playlist

import androidx.room.ColumnInfo

data class PlaylistWithCountEntity (
    @ColumnInfo(name = "playlist_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "artwork_url")
    val artwork: String? = null,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)