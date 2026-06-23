package com.example.core_database.datasource.user

import androidx.room.Query
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserSearchSongCrossRefEntity
import kotlinx.coroutines.flow.Flow

interface SearchSongLocalDataSource {
    fun getSearchedSongs(userId: Int): Flow<List<SongEntity>>
    suspend fun getAllSearchSongIds(userId: Int): List<String>
    suspend fun insert(crossRef: UserSearchSongCrossRefEntity)
    suspend fun delete(crossRef: UserSearchSongCrossRefEntity)
    suspend fun clearAll(userId: Int)
}