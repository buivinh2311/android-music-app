package com.example.core_database.entity.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "artist_song_cross_ref",
    primaryKeys = ["artist_id", "song_id"],
    indices = [Index("artist_id"), Index("song_id")]
)
data class ArtistSongCrossRefEntity(
    @ColumnInfo(name = "artist_id")
    val artistId: Int,

    @ColumnInfo(name = "song_id")
    val songId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
