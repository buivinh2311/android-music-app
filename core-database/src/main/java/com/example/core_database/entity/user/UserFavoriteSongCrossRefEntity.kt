package com.example.core_database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_favorite_song_cross_ref",
    primaryKeys = ["user_id", "song_id"],
    indices = [Index("user_id"), Index("song_id")]
)
data class UserFavoriteSongCrossRefEntity (
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "song_id")
    val songId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)