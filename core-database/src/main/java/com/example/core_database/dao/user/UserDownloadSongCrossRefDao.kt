package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.song.SongEntity
import com.example.core_model.download.DownloadState
import com.example.core_database.entity.user.UserDownloadSongCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDownloadSongCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDownloadSong: UserDownloadSongCrossRefEntity)

    @Query(
        "SELECT EXISTS( " +
                "SELECT 1 FROM user_download_song_cross_ref u " +
                "WHERE u.user_id = :userId AND song_id = :songId" +
                ")"
    )
    fun isDownloadSong(userId: Int, songId: String): Flow<Boolean>

    @Query(
        "SELECT s.* FROM user_download_song_cross_ref u " +
                "INNER JOIN songs s ON u.song_id = s.song_id " +
                "WHERE user_id = :userId " +
                "ORDER BY u.created_at"
    )
    fun getDownloadSongs(userId: Int): Flow<List<SongEntity>>

    @Query(
        "SELECT COUNT(song_id) FROM user_download_song_cross_ref " +
                "WHERE user_id = :userId"
    )
    fun getDownloadSongCount(userId: Int): Flow<Int>

    @Query(
        "SELECT * FROM user_download_song_cross_ref " +
                "WHERE user_id = :userId AND song_id = :songId"
    )
    suspend fun getDownloadInfo(userId: Int, songId: String): UserDownloadSongCrossRefEntity?

    @Query(
        "SELECT song_id FROM user_download_song_cross_ref " +
                "WHERE user_id = :userId"
    )
    suspend fun getAllDownloadSongIds(userId: Int): List<String>

    @Query(
        "DELETE FROM user_download_song_cross_ref " +
                "WHERE user_id = :userId AND song_id = :songId"
    )
    suspend fun delete(userId: Int, songId: String)
}