package com.example.core_network.datasource

import com.example.core_network.dto.AlbumDto
import com.example.core_network.dto.AlbumListDto
import com.example.core_network.dto.PagingParamRequest

interface AlbumRemoteDataSource {

    suspend fun loadAlbumPaging(param: PagingParamRequest): List<AlbumDto>
    suspend fun getAllAlbums(): List<AlbumDto>
    suspend fun getTopAlbums(limit: Int): List<AlbumDto>
    suspend fun getAlbumById(albumId: Int): AlbumDto?
}