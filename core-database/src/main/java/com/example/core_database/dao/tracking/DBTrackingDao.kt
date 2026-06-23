package com.example.core_database.dao.tracking

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core_database.entity.tracking.DBTrackingEntity

@Dao
interface DBTrackingDao {
    @Insert
    suspend fun insert(tracking: DBTrackingEntity)

    @Query("SELECT * FROM db_tracking LIMIT 1")
    suspend fun getTracking(): DBTrackingEntity?

    @Query(
        """
        UPDATE db_tracking
        SET last_recommended_song_updated = :timestamp
        """
    )
    suspend fun updateRecommendedSongTimestamp(
        timestamp: Long
    )

    @Query(
        """
        UPDATE db_tracking
        SET last_for_you_song_updated = :timestamp
        """
    )
    suspend fun updateForYouSongTimestamp(
        timestamp: Long
    )

    @Query(
        """
        UPDATE db_tracking
        SET last_most_heard_song_updated = :timestamp
        """
    )
    suspend fun updateMostHeardTimestamp(
        timestamp: Long
    )

    @Query(
        """
        UPDATE db_tracking
        SET last_album_updated = :timestamp
        """
    )
    suspend fun updateAlbumTimestamp(
        timestamp: Long
    )

    @Query(
        """
        UPDATE db_tracking
        SET last_artist_updated = :timestamp
        """
    )
    suspend fun updateArtistTimestamp(
        timestamp: Long
    )

    @Query(
        """
        UPDATE db_tracking
        SET last_clean_up_time = :timestamp
        """
    )
    suspend fun updateCleanUpTimestamp(
        timestamp: Long
    )

    @Query("DELETE FROM db_tracking")
    suspend fun clearAll()
}