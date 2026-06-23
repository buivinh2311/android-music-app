package com.example.infrastructure.source.tracking

import com.example.core_database.dao.tracking.DBTrackingDao
import com.example.core_database.datasource.tracking.DBTrackingDataSource
import com.example.core_database.entity.tracking.DBTrackingEntity
import javax.inject.Inject

class DBTrackingDataSourceImpl @Inject constructor(
    private val dbTrackingDao: DBTrackingDao
): DBTrackingDataSource {
    override suspend fun insert(tracking: DBTrackingEntity) {
        dbTrackingDao.insert(tracking)
    }

    override suspend fun getTracking(): DBTrackingEntity? {
        return dbTrackingDao.getTracking()
    }

    override suspend fun updateRecommendedSongTimestamp(timestamp: Long) {
        dbTrackingDao.updateRecommendedSongTimestamp(timestamp)
    }

    override suspend fun updateForYouSongTimestamp(timestamp: Long) {
        dbTrackingDao.updateForYouSongTimestamp(timestamp)
    }

    override suspend fun updateMostHeardTimestamp(timestamp: Long) {
        dbTrackingDao.updateMostHeardTimestamp(timestamp)
    }

    override suspend fun updateAlbumTimestamp(timestamp: Long) {
        dbTrackingDao.updateAlbumTimestamp(timestamp)
    }

    override suspend fun updateArtistTimestamp(timestamp: Long) {
        dbTrackingDao.updateArtistTimestamp(timestamp)
    }

    override suspend fun updateCleanUpTimestamp(timestamp: Long) {
        dbTrackingDao.updateCleanUpTimestamp(timestamp)
    }

    override suspend fun clearAll() {
        dbTrackingDao.clearAll()
    }
}