package com.example.core_database.datasource.tracking

import com.example.core_database.entity.tracking.DBTrackingEntity

interface DBTrackingDataSource {
    suspend fun insert(tracking: DBTrackingEntity)
    suspend fun getTracking(): DBTrackingEntity?
    suspend fun updateRecommendedSongTimestamp(timestamp: Long)
    suspend fun updateForYouSongTimestamp(timestamp: Long)
    suspend fun updateMostHeardTimestamp(timestamp: Long)
    suspend fun updateAlbumTimestamp(timestamp: Long)
    suspend fun updateArtistTimestamp(timestamp: Long)
    suspend fun updateCleanUpTimestamp(timestamp: Long)
    suspend fun clearAll()
}