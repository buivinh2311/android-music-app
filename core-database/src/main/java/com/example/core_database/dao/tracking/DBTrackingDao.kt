package com.example.core_database.dao.tracking

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core_database.entity.tracking.DBTrackingEntity

@Dao
interface DBTrackingDao {
    @Insert
    suspend fun insert(tracking: DBTrackingEntity)

    @Query("SELECT * FROM db_tracking WHERE last_album_updated <> 0")
    suspend fun getAlbumTracking(): DBTrackingEntity?

    @Query("SELECT * FROM db_tracking WHERE last_artist_updated <> 0")
    suspend fun getArtistTracking(): DBTrackingEntity?

    @Query("SELECT * FROM db_tracking WHERE last_song_updated <> 0")
    suspend fun getSongTracking(): DBTrackingEntity?

    @Query("DELETE FROM db_tracking")
    suspend fun clearAll()
}