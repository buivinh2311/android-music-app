package com.example.core_database.datasource.user

import androidx.room.Query
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserSearchSongCrossRefEntity

interface SearchSongLocalDataSource {
    fun getSearchedSongs(userId: Int): List<SongEntity>
    suspend fun insert(crossRef: UserSearchSongCrossRefEntity)
    suspend fun delete(crossRef: UserSearchSongCrossRefEntity)
    @Query("DELETE FROM user_searched_song_cross_ref")
    suspend fun clearAll()
}