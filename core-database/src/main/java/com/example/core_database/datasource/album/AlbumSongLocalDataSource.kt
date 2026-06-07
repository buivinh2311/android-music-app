package com.example.core_database.datasource.album

import com.example.core_database.entity.album.AlbumSongCrossRefEntity

interface AlbumSongLocalDataSource {
    suspend fun insertAll(crossRefs: List<AlbumSongCrossRefEntity>)
    suspend fun getCrossRefByAlbumId(albumId: Int): AlbumSongCrossRefEntity?
    suspend fun clearAll()
}