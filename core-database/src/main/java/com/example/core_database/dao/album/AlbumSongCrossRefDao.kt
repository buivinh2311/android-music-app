package com.example.core_database.dao.album

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.album.AlbumSongCrossRefEntity

@Dao
interface AlbumSongCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<AlbumSongCrossRefEntity>)

    @Query("SELECT * FROM album_song_cross_ref WHERE album_id = :albumId")
    suspend fun getCrossRefById(albumId: Int): AlbumSongCrossRefEntity

    @Query("DELETE FROM album_song_cross_ref")
    suspend fun clearAll()
}