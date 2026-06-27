package com.example.infrastructure.source.album

import com.example.core_network.api.AlbumApi
import com.example.core_network.datasource.AlbumRemoteDataSource
import com.example.core_network.dto.AlbumDto
import com.example.core_network.dto.PagingParamRequest
import com.example.core_network.utils.safeApiCall
import javax.inject.Inject

class AlbumRemoteDataSourceImpl @Inject constructor(
    private val albumApi: AlbumApi
): AlbumRemoteDataSource {
    override suspend fun loadAlbumPaging(param: PagingParamRequest): List<AlbumDto> {
        return safeApiCall("loadAlbumPaging") {
            albumApi.loadAlbumPaging(param.offset, param.limit).albumListDto
        }
    }

    override suspend fun getAllAlbums(): List<AlbumDto> {
        return safeApiCall("getAllAlbums") {
            albumApi.getAllAlbum().albumListDto
        }
    }

    override suspend fun getTopAlbums(limit: Int): List<AlbumDto> {
        return safeApiCall("getTopAlbums") {
            albumApi.getAllAlbum().albumListDto
                .sortedWith(
                    compareByDescending<AlbumDto> { it.size }
                        .thenBy { it.id }
                )
                .take(limit)
        }
    }

    override suspend fun getAlbumById(albumId: Int): AlbumDto {
        return safeApiCall("getAlbumById") {
            albumApi.getAlbumById(albumId)
        }
    }
}