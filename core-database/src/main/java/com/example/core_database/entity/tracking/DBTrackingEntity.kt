package com.example.core_database.entity.tracking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_tracking")
data class DBTrackingEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo("last_artist_updated")
    val lastArtistUpdated: Long = 0,

    @ColumnInfo("last_album_updated")
    val lastAlbumUpdated: Long = 0,

    @ColumnInfo("last_song_updated")
    val lastSongUpdated: Long = 0
)