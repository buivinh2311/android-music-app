package com.example.core_database.dao.playlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity
import com.example.core_database.entity.song.SongEntity
import kotlinx.coroutines.flow.Flow

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
    suspend fun getCrossRefByPlaylistId(playlistId: Int): List<PlaylistSongCrossRefEntity>

    @Query(
        "SELECT EXISTS (" +
                "SELECT 1 FROM playlist_song_cross_ref " +
                "WHERE playlist_id = :playlistId AND song_id = :songId" +
                ")"
    )
    suspend fun isSongInPlaylist(playlistId: Int, songId: String): Boolean

    @Query(
        "SELECT s.* FROM playlist_song_cross_ref p " +
                "INNER JOIN songs s ON p.song_id = s.song_id " +
                "WHERE p.playlist_id = :playlistId " +
                "ORDER BY p.created_at"
    )
    fun getSongsInPlaylist(playlistId: Int): Flow<List<SongEntity>>

    @Query("SELECT DISTINCT song_id FROM playlist_song_cross_ref")
    suspend fun getAllSongIds(): List<String>

    @Query(
        "DELETE FROM playlist_song_cross_ref " +
                "WHERE playlist_id = :playlistId AND song_id = :songId"
    )
    suspend fun delete(playlistId: Int, songId: String)

    @Query(
        "DELETE FROM playlist_song_cross_ref " +
                "WHERE playlist_id = :playlistId"
    )
    suspend fun deleteByPlaylist(playlistId: Int)

    @Query("DELETE FROM playlist_song_cross_ref")
    suspend fun clearAll()
}