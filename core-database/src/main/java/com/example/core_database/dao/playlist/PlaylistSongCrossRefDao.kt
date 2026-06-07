package com.example.core_database.dao.playlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity

@Dao
interface PlaylistSongCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: PlaylistSongCrossRefEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<PlaylistSongCrossRefEntity>)

    @Query(
        "SELECT * FROM playlist_song_cross_ref " +
                "WHERE playlist_id = :playlistId"
    )
    suspend fun getCrossRefByPlaylistId(playlistId: Int): PlaylistSongCrossRefEntity?

    @Query("DELETE FROM playlist_song_cross_ref")
    suspend fun clearAll()
}