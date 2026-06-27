package com.example.core_database.dao.song

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.song.SongRemoteKeysEntity

@Dao
interface SongRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<SongRemoteKeysEntity>)

    @Query("SELECT * FROM song_remote_keys WHERE song_id = :songId")
    suspend fun getSongRemoteKeysById(songId: String): SongRemoteKeysEntity?

    @Query("DELETE FROM songs")
    suspend fun clearAll()
}