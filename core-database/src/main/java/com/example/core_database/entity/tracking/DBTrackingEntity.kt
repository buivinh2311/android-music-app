package com.example.core_database.entity.tracking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_tracking")
data class DBTrackingEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("last_artist_updated")
    val lastArtistUpdated: Long = 0,

    @ColumnInfo("last_album_updated")
    val lastAlbumUpdated: Long = 0,

    @ColumnInfo("last_recommended_song_updated")
    val lastRecommendedSongUpdated: Long = 0,

    @ColumnInfo("last_most_heard_song_updated")
    val lastMostHeardSongUpdated: Long = 0,

    @ColumnInfo("last_for_you_song_updated")
    val lastForYouSongUpdated: Long = 0,

    @ColumnInfo("last_song_updated")
    val lastSongUpdated: Long = 0,

    @ColumnInfo("last_clean_up_time")
    val lastCleanUpTime: Long = 0
)