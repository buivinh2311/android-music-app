package com.example.core_database.entity.artist

import androidx.room.ColumnInfo

data class FollowedArtistEntity (
    @ColumnInfo(name = "artist_name")
    val name: String,

    @ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @ColumnInfo(name = "interested")
    val interested: Int = 0
)