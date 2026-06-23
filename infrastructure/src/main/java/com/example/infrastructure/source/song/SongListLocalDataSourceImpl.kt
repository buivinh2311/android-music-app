package com.example.infrastructure.source.song

import com.example.core_database.dao.song.SongListDao
import com.example.core_database.datasource.song.SongListLocalDataSource
import com.example.core_database.entity.song.SongListEntity
import javax.inject.Inject

class SongListLocalDataSourceImpl @Inject constructor(
    private val songListDao: SongListDao
): SongListLocalDataSource {
    override suspend fun insertAll(songLists: List<SongListEntity>) {
        songListDao.insertAll(songLists)
    }

    override suspend fun getSongIds(listType: String): List<String> {
        return songListDao.getSongIds(listType)
    }

    override suspend fun deleteByType(type: String) {
        songListDao.deleteByType(type)
    }

    override suspend fun clearAll() {
        songListDao.clearAll()
    }
}