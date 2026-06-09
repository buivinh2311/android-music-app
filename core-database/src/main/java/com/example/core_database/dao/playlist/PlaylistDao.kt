package com.example.core_database.dao.playlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.playlist.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists WHERE playlist_id = :playlistId")
    suspend fun getPlaylistById(playlistId: Int): PlaylistEntity?

    @Query(
        "SELECT p.* FROM playlists p " +
                "LEFT JOIN playlist_song_cross_ref ps ON p.playlist_id = ps.playlist_id " +
                "GROUP BY p.playlist_id " +
                "ORDER BY p.created_at DESC"
    )
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    @Query("SELECT MAX(playlist_id) FROM playlists")
    suspend fun getMaxPlaylistId(): Int?

    @Query("UPDATE playlists SET size = size + :size WHERE playlist_id = :playlistId")
    suspend fun updateSize(playlistId: Int, size: Int)

    @Query("DELETE FROM playlists WHERE playlist_id = :playlistId")
    suspend fun delete(playlistId: Int)
}