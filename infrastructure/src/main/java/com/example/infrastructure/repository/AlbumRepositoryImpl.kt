package com.example.infrastructure.repository

import com.example.core_database.datasource.album.AlbumLocalDataSource
import com.example.core_database.datasource.tracking.DBTrackingDataSource
import com.example.core_database.datasource.user.FavoriteAlbumLocalDataSource
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.AlbumRepository
import com.example.core_model.Album
import com.example.core_network.datasource.AlbumRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.core_utils.util.AppUtil
import com.example.infrastructure.database.AppDatabase
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.local.toModels
import com.example.infrastructure.mapper.remote.toModel
import com.example.infrastructure.mapper.remote.toModels
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val albumLocalDataSource: AlbumLocalDataSource,
    private val albumRemoteDataSource: AlbumRemoteDataSource,
    private val favoriteAlbumLocalDataSource: FavoriteAlbumLocalDataSource,
    private val dbTrackingDataSource: DBTrackingDataSource,
    private val userManager: UserManager
): AlbumRepository {
    private fun isCacheExpired(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > AppUtil.CACHE_TIMEOUT
    }

    private suspend fun getValidAlbumName(): Set<String> {
        val userId = userManager.getCurrentUserId()
        return buildSet {
            addAll(
                favoriteAlbumLocalDataSource.getAllFavoriteAlbumNames(userId)
            )
        }
    }

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
        val tracking = dbTrackingDataSource.getTracking() ?: return localAlbums.toModels()
        return if(!isCacheExpired(tracking.lastAlbumUpdated)
            && localAlbums.size >= limit) {
            localAlbums.toModels()
        } else {
            try {
                val remoteAlbum = albumRemoteDataSource.getTopAlbums(limit)
                albumLocalDataSource.deleteAlbumNotIn(getValidAlbumName())
                insertAll(remoteAlbum.toModels())
                dbTrackingDataSource.updateAlbumTimestamp(System.currentTimeMillis())
                remoteAlbum.toModels()
            } catch (_: Exception) {
                localAlbums.toModels()
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