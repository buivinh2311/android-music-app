package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserRecentSongCrossRefEntity

@Dao
interface UserRecentSongCrossRefDao {
    @Query(
        "SELECT s.* FROM user_recent_song_cross_ref u " +
                "        INNER JOIN songs s ON s.song_id = u.song_id " +
                "        WHERE u.user_id = :userId " +
                "        ORDER BY u.updated_at DESC LIMIT 1"
    )
    fun getMostRecentSong(userId: Int): SongEntity?

    @Query("SELECT * FROM user_recent_song_cross_ref " +
            "WHERE song_id = :songId AND user_id = :userId")
    suspend fun getUserRecentSongById(songId: String, userId: Int): UserRecentSongCrossRefEntity?

    @Query(
        "SELECT s.* FROM user_recent_song_cross_ref u " +
                "INNER JOIN songs s ON u.song_id = s.song_id " +
                "WHERE user_id = :userId " +
                "ORDER BY updated_at DESC " +
                "LIMIT 100"
    )
    fun getRecentSongs(userId: Int): List<SongEntity>

    @Query(
        "SELECT song_id FROM user_recent_song_cross_ref " +
                "WHERE user_id = :userId " +
                "ORDER BY replay DESC " +
                "LIMIT :limit"
    )
    fun getMostListenedSongIdsForUser(userId: Int, limit: Int): List<String>

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insert(crossRef: UserRecentSongCrossRefEntity)

    @Query(
        "UPDATE user_recent_song_cross_ref SET replay = replay + 1, updated_at = :updatedAt " +
                "WHERE song_id = :songId AND user_id = :userId"
    )
    suspend fun update(songId: String, userId: Int, updatedAt: Long)

    @Delete
    suspend fun delete(crossRef: UserRecentSongCrossRefEntity)


    @Query("DELETE FROM user_recent_song_cross_ref")
    suspend fun clearAll()
}