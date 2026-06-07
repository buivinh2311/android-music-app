package com.example.core_database.entity.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "playlist_song_cross_ref",
    primaryKeys = ["playlist_id", "song_id"],
    indices = [Index("playlist_id"), Index("song_id")]
)
data class PlaylistSongCrossRefEntity(
    @ColumnInfo(name = "playlist_id")
    val playlistId: Int,

    @ColumnInfo(name = "song_id")
    val songId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
