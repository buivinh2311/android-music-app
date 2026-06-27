package com.example.core_database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "user_favorite_artist_cross_ref",
    primaryKeys = ["user_id", "artist_name"],
    indices = [Index("user_id"), Index("artist_name")]
)
data class UserFavoriteArtistCrossRefEntity (
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "artist_name")
    val artistName: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)