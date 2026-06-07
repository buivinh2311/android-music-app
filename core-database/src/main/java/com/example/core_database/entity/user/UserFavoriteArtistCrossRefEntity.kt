package com.example.core_database.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_favorite_artist_cross_ref",
    primaryKeys = ["user_id", "artist_id"],
    indices = [Index("user_id"), Index("artist_id")]
)
data class UserFavoriteArtistCrossRefEntity (
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "artist_id")
    val artistId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)