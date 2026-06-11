package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteSongCrossRefDao
import com.example.core_database.datasource.user.FavoriteSongLocalDataSource
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteSongCrossRefEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteSongLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserFavoriteSongCrossRefDao
): FavoriteSongLocalDataSource {
    override suspend fun insert(userFavoriteSong: UserFavoriteSongCrossRefEntity) {
        crossRefDao.insert(userFavoriteSong)
    }

    override fun isFavoriteSong(userId: Int, songId: String): Flow<Boolean> {
        return crossRefDao.isFavoriteSong(userId, songId)
    }

    override fun getFavoriteSongs(userId: Int): Flow<List<SongEntity>> {
        return crossRefDao.getFavoriteSong(userId)
    }

    override suspend fun delete(userId: Int, songId: String) {
        crossRefDao.delete(userId, songId)
    }


}