package com.example.core_database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    "user_favorite_album_cross_ref",
    primaryKeys = ["user_id", "album_name"],
    indices = [Index("user_id"), Index("album_name")]
)
data class UserFavoriteAlbumCrossRefEntity (
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "album_name")
    val albumName: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)