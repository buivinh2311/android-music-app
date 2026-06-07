package com.example.infrastructure.source.album

import androidx.paging.PagingSource
import com.example.core_database.dao.album.AlbumDao
import com.example.core_database.datasource.album.AlbumLocalDataSource
import com.example.core_database.entity.album.AlbumEntity
import javax.inject.Inject

class AlbumLocalDataSourceImpl @Inject constructor(
    private val albumDao: AlbumDao
): AlbumLocalDataSource {
    override fun getAlbumPagingSource(): PagingSource<Int, AlbumEntity> {
        return albumDao.getAlbumPagingSource()
    }

    override fun getNAlbumPagingSource(limit: Int): PagingSource<Int, AlbumEntity> {
        return albumDao.getNAlbumPagingSource(limit)
    }

    override suspend fun getTopAlbums(limit: Int): List<AlbumEntity> {
        return albumDao.getTopAlbums(limit)
    }

    override suspend fun getAlbumById(albumId: Int): AlbumEntity? {
        return albumDao.getAlbumById(albumId)
    }

    override suspend fun getAlbumByName(albumName: String): AlbumEntity? {
        return albumDao.getAlbumByName(albumName)
    }

    override suspend fun insertAll(albums: List<AlbumEntity>) {
        albumDao.insertAll(albums)
    }

    override suspend fun clearAll(albums: List<AlbumEntity>) {
        albumDao.clearAll()
    }
}