package com.example.infrastructure.source.album

import com.example.core_database.dao.album.AlbumSongCrossRefDao
import com.example.core_database.datasource.album.AlbumSongLocalDataSource
import com.example.core_database.entity.album.AlbumSongCrossRefEntity
import javax.inject.Inject

class AlbumSongLocalDataSourceImpl @Inject constructor(
    private val albumSongCrossRefDao: AlbumSongCrossRefDao
): AlbumSongLocalDataSource {
    override suspend fun insertAll(crossRefs: List<AlbumSongCrossRefEntity>) {
        albumSongCrossRefDao.insertAll(crossRefs)
    }

    override suspend fun getCrossRefByAlbumId(albumId: Int): AlbumSongCrossRefEntity? {
        return albumSongCrossRefDao.getCrossRefById(albumId)
    }

    override suspend fun clearAll() {
        albumSongCrossRefDao.clearAll()
    }
}