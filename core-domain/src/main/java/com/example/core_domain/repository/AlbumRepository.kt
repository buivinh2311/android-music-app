package com.example.core_domain.repository

import com.example.core_model.Album
import com.example.core_network.dto.PagingParamRequest

interface AlbumRepository {
    suspend fun loadAlbumPaging(param: PagingParamRequest): List<Album>
    suspend fun getTopAlbums(limit: Int): List<Album>
    suspend fun getAlbumById(albumId: Int): Album?
    suspend fun getAlbumByName(albumName: String): Album?
    suspend fun insertAll(albums: List<Album>)
}