package com.example.core_database.dao.song

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.song.SongListEntity

@Dao
interface SongListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songLists: List<SongListEntity>)

    @Query("SELECT song_id FROM song_lists WHERE list_type = :listType")
    suspend fun getSongIds(listType: String): List<String>

    @Query("DELETE FROM song_lists WHERE list_type = :type")
    suspend fun deleteByType(type: String)

    @Query("DELETE FROM song_lists")
    suspend fun clearAll()
}