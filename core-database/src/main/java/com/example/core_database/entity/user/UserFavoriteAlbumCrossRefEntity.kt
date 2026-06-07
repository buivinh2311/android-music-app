package com.example.core_database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "user_favorite_album_cross_ref",
    primaryKeys = ["user_id", "album_id"],
    indices = [Index("user_id"), Index("album_id")]
)
data class UserFavoriteAlbumCrossRefEntity (
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "album_id")
    val albumId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)