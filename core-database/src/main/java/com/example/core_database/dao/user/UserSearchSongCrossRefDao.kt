package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserSearchSongCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSearchSongCrossRefDao {
    @Query(
        "SELECT s.* FROM user_searched_song_cross_ref u " +
                "INNER JOIN songs s ON u.song_id = s.song_id " +
                "WHERE user_id = :userId " +
                "ORDER BY created_at DESC " +
                "LIMIT 100"
    )
    fun getSearchedSongs(userId: Int): List<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: UserSearchSongCrossRefEntity)

    @Delete
    suspend fun delete(crossRef: UserSearchSongCrossRefEntity)


    @Query("DELETE FROM user_searched_song_cross_ref")
    suspend fun clearAll()
}