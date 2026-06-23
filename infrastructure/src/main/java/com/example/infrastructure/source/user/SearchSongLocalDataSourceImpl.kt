package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserSearchSongCrossRefDao
import com.example.core_database.datasource.user.SearchSongLocalDataSource
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserSearchSongCrossRefEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchSongLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserSearchSongCrossRefDao
): SearchSongLocalDataSource {
    override fun getSearchedSongs(userId: Int): Flow<List<SongEntity>> {
        return crossRefDao.getSearchedSongs(userId)
    }

    override suspend fun getAllSearchSongIds(userId: Int): List<String> {
        return crossRefDao.getAllSearchSongIds(userId)
    }

    override suspend fun insert(crossRef: UserSearchSongCrossRefEntity) {
        crossRefDao.insert(crossRef)
    }

    override suspend fun delete(crossRef: UserSearchSongCrossRefEntity) {
        crossRefDao.delete(crossRef)
    }

    override suspend fun clearAll(userId: Int) {
        crossRefDao.clearAll(userId)
    }
}