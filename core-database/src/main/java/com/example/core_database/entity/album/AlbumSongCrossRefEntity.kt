package com.example.core_database.entity.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "album_song_cross_ref",
    primaryKeys = ["album_id", "song_id"],
    indices = [Index("album_id"), Index("song_id")]
)
data class AlbumSongCrossRefEntity (
    @ColumnInfo(name = "album_id")
    val albumId: Int,

    @ColumnInfo(name = "song_id")
    val songId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)