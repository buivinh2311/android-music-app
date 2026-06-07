package com.example.infrastructure.repository

import com.example.core_database.datasource.album.AlbumLocalDataSource
import com.example.core_domain.repository.AlbumRepository
import com.example.core_model.Album
import com.example.core_network.datasource.AlbumRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.local.toModels
import com.example.infrastructure.mapper.remote.toModel
import com.example.infrastructure.mapper.remote.toModels
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val albumLocalDataSource: AlbumLocalDataSource,
    private val albumRemoteDataSource: AlbumRemoteDataSource
): AlbumRepository {
    override suspend fun loadAlbumPaging(
        param: PagingParamRequest
    ): List<Album> {
        return try {
            albumRemoteDataSource.loadAlbumPaging(param).toModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getTopAlbums(limit: Int): List<Album> {
        val localAlbums = albumLocalDataSource.getTopAlbums(limit)
        return if(localAlbums.size >= limit) {
            localAlbums.toModels()
        } else {
            try {
                val remoteAlbum = albumRemoteDataSource.getTopAlbums(limit)
                insertAll(remoteAlbum.toModels())
                remoteAlbum.toModels()
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun getAlbumById(albumId: Int): Album? {
        val localAlbum = albumLocalDataSource.getAlbumById(albumId)
        return localAlbum?.toModel() ?: try {
            albumRemoteDataSource.getAlbumById(albumId)?.toModel()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getAlbumByName(albumName: String): Album? {
        val localAlbum = albumLocalDataSource.getAlbumByName(albumName)
        return localAlbum?.toModel() ?: try {
            albumRemoteDataSource.getAllAlbums()
                .find { it.name == albumName }
                ?.toModel()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun insertAll(albums: List<Album>) {
        val albumEntities = albums.map {
            it.toEntity()
        }
        albumLocalDataSource.insertAll(albumEntities)
    }
}