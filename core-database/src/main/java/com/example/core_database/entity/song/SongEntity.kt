package com.example.core_database.entity.song

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey
    @ColumnInfo(name = "song_id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "album")
    val album: String?,

    @ColumnInfo(name = "artist")
    val artist: String,

    @ColumnInfo(name = "source_url")
    val sourceUrl: String,

    @ColumnInfo(name = "artwork_url")
    val artworkUrl: String,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @ColumnInfo(name = "favorite")
    val favorite: Int,

    @ColumnInfo(name = "counter")
    val counter: Int,

    @ColumnInfo(name = "replay")
    val replay: Int = 0
)
