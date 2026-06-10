package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserRecentSongCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRecentSongCrossRefDao {
    @Query(
        "SELECT s.* FROM user_recent_song_cross_ref u " +
                "INNER JOIN songs s ON u.song_id = s.song_id " +
                "WHERE user_id = :userId " +
                "ORDER BY created_at DESC " +
                "LIMIT :limit"
    )
    fun getLimitRecentSongs(userId: Int, limit: Int): Flow<List<SongEntity>>

    @Query(
        "SELECT s.* FROM user_recent_song_cross_ref u " +
                "INNER JOIN songs s ON u.song_id = s.song_id " +
                "WHERE user_id = :userId " +
                "ORDER BY created_at DESC "
    )
    fun getAllRecentSongs(userId: Int): Flow<List<SongEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(crossRef: UserRecentSongCrossRefEntity)

    @Delete
    suspend fun delete(crossRef: UserRecentSongCrossRefEntity)

    @Query("DELETE FROM user_recent_song_cross_ref WHERE user_id = :userId")
    suspend fun clearAll(userId: Int)
}