package com.example.core_database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "user_recent_song_cross_ref",
    primaryKeys = ["song_id", "user_id"],
    indices = [Index("user_id"), Index("song_id")]
)
data class UserRecentSongCrossRefEntity(
    @ColumnInfo(name = "song_id")
    val songId: String,

    @ColumnInfo(name = "user_id")
    val userId: Int = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "replay")
    val replay: Int = 1
)