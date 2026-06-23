package com.example.core_database.datasource.album

import androidx.paging.PagingSource
import androidx.room.Query
import com.example.core_database.entity.album.AlbumEntity

interface AlbumLocalDataSource {
    fun getAlbumPagingSource(): PagingSource<Int, AlbumEntity>
    fun getNAlbumPagingSource(limit: Int): PagingSource<Int, AlbumEntity>
    suspend fun getTopAlbums(limit: Int): List<AlbumEntity>
    suspend fun getAlbumById(albumId: Int): AlbumEntity?
    suspend fun getAlbumByName(albumName: String): AlbumEntity?
    suspend fun insertAll(albums: List<AlbumEntity>)
    suspend fun delete(albumId: Int)
    suspend fun deleteAlbumNotIn(validNames: Set<String>)
    suspend fun clearAll(albums: List<AlbumEntity>)
}