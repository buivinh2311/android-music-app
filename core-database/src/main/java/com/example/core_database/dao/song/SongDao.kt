package com.example.core_database.dao.song

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.song.SongEntity

@Dao
interface SongDao {
    @Query("SELECT * FROM songs ORDER BY song_id")
    fun getSongPagingSource(): PagingSource<Int, SongEntity>

    @Query("SELECT * FROM songs ORDER BY song_id LIMIT :limit")
    fun getNSongPagingSource(limit: Int): PagingSource<Int, SongEntity>

    @Query(
        "SELECT s.* FROM songs s " +
                "INNER JOIN album_song_cross_ref a ON s.song_id = a.song_id " +
                "WHERE a.album_id = :albumId " +
                "ORDER BY a.created_at"
    )
    suspend fun getSongsByAlbumId(albumId: Int): List<SongEntity>

    @Query("SELECT s.* FROM songs s WHERE s.album = :albumName ORDER BY s.song_id")
    suspend fun getSongsByAlbumName(albumName: String): List<SongEntity>

    @Query(
        "SELECT s.* FROM songs s " +
                "INNER JOIN artist_song_cross_ref a ON s.song_id = a.song_id " +
                "WHERE a.artist_id = :artistId " +
                "ORDER BY a.created_at"
    )
    suspend fun getSongsByArtistId(artistId: Int): List<SongEntity>

    @Query(
        "SELECT s.* FROM songs s " +
            "WHERE s.artist = :artistName " +
            "OR s.artist LIKE '% ft ' || :artistName || ' ft %' " +
            "OR s.artist LIKE '% ft ' || :artistName " +
            "OR s.artist LIKE :artistName || ' ft %' " +
            "ORDER BY s.song_id"
    )
    suspend fun getSongsByArtistName(artistName: String): List<SongEntity>

    @Query(
        "SELECT s.* FROM songs s " +
                "INNER JOIN playlist_song_cross_ref p ON s.song_id = p.song_id " +
                "WHERE p.playlist_id = :playlistId " +
                "ORDER BY p.created_at"
    )
    suspend fun getSongsInPlaylist(playlistId: Int): List<SongEntity>

    @Query("SELECT * FROM songs WHERE song_id = :songId")
    suspend fun getSongById(songId: String): SongEntity?

    @Query(
        "SELECT s.* FROM songs s " +
                "INNER JOIN song_lists l ON s.song_id = l.song_id " +
                "WHERE l.list_type = :type " +
                "ORDER BY s.counter, s.song_id " +
                "LIMIT :limit"
    )
    suspend fun getRecommendedSongs(limit: Int, type: String): List<SongEntity>

    @Query(
        "SELECT * FROM songs s " +
                "INNER JOIN song_lists l ON s.song_id = l.song_id " +
                "WHERE l.list_type = :type " +
                "ORDER BY s.counter DESC, s.song_id " +
                "LIMIT :limit"
    )
    suspend fun getMostHeardSongs(limit: Int, type: String): List<SongEntity>

    @Query(
        "SELECT * FROM songs s " +
                "INNER JOIN song_lists l ON s.song_id = l.song_id " +
                "WHERE l.list_type = :type " +
                "ORDER BY s.song_id " +
                "LIMIT :limit"
    )
    suspend fun getForYouSongs(limit: Int, type: String): List<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: SongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songs: List<SongEntity>)

    @Query("UPDATE songs SET counter = counter + 1 WHERE song_id = :songId")
    suspend fun updateCounter(songId: String)

    @Query("DELETE FROM songs WHERE song_id = :songId")
    suspend fun delete(songId: String)

    @Query("DELETE FROM songs")
    suspend fun clearAll()
}