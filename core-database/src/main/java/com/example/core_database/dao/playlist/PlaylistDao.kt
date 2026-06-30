package com.example.core_database.dao.playlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.playlist.PlaylistEntity
import com.example.core_database.entity.playlist.PlaylistWithCountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: PlaylistEntity)

    @Query(
        "SELECT p.*, COUNT(ps.song_id) AS size FROM playlists p " +
                "LEFT JOIN playlist_song_cross_ref ps ON p.playlist_id = ps.playlist_id " +
                "WHERE p.playlist_id = :playlistId " +
                "GROUP BY p.playlist_id"
    )
    fun getPlaylistById(playlistId: Int): Flow<PlaylistWithCountEntity?>

    @Query(
        "SELECT p.*, COUNT(ps.song_id) AS size FROM playlists p " +
                "LEFT JOIN playlist_song_cross_ref ps ON p.playlist_id = ps.playlist_id " +
                "GROUP BY p.playlist_id " +
                "ORDER BY p.created_at DESC"
    )
    fun getAllPlaylists(): Flow<List<PlaylistWithCountEntity>>

    @Query(
        "SELECT p.*, COUNT(ps.song_id) AS size FROM playlists p " +
                "LEFT JOIN playlist_song_cross_ref ps ON p.playlist_id = ps.playlist_id " +
                "GROUP BY p.playlist_id " +
                "ORDER BY p.created_at DESC " +
                "LIMIT :limit"
    )
    fun getLimitPlaylists(limit: Int): Flow<List<PlaylistWithCountEntity>>

    @Query("SELECT MAX(playlist_id) FROM playlists")
    suspend fun getMaxPlaylistId(): Int?

    @Query("UPDATE playlists SET artwork_url = :artwork WHERE playlist_id = :playlistId")
    suspend fun updateArtwork(playlistId: Int, artwork: String)

    @Query("UPDATE playlists SET name = :newName WHERE playlist_id = :playlistId")
    suspend fun rename(playlistId: Int, newName: String)

    @Query("DELETE FROM playlists WHERE playlist_id = :playlistId")
    suspend fun delete(playlistId: Int)
}