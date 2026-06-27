package com.example.infrastructure.repository

import com.example.core_database.datasource.artist.ArtistLocalDataSource
import com.example.core_database.datasource.tracking.DBTrackingDataSource
import com.example.core_database.datasource.user.FavoriteArtistLocalDataSource
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.ArtistRepository
import com.example.core_model.Artist
import com.example.core_network.datasource.ArtistRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.core_utils.util.AppUtil
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.local.toModels
import com.example.infrastructure.mapper.remote.toModel
import com.example.infrastructure.mapper.remote.toModels
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val favoriteArtistLocalDataSource: FavoriteArtistLocalDataSource,
    private val dbTrackingDataSource: DBTrackingDataSource,
    private val userManager: UserManager
): ArtistRepository {
    private fun isCacheExpired(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > AppUtil.CACHE_TIMEOUT
    }

    private suspend fun getValidArtistName(): Set<String> {
        val userId = userManager.getCurrentUserId()
        return buildSet {
            addAll(
                favoriteArtistLocalDataSource.getAllFollowedArtistNames(userId)
            )
        }
    }

    override suspend fun loadArtistPaging(
        param: PagingParamRequest
    ): List<Artist> {
        return try {
            artistRemoteDataSource.loadArtistPaging(param).toModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getAllArtists(): List<Artist> {
        return try {
            artistRemoteDataSource.getAllArtists().toModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getTopArtists(limit: Int): List<Artist> {
        val localArtists = artistLocalDataSource.getTopArtists(limit)
        val tracking = dbTrackingDataSource.getTracking() ?: return localArtists.toModels()
        return if(!isCacheExpired(tracking.lastArtistUpdated)
            && localArtists.size >= limit) {
            localArtists.toModels()
        } else {
            try {
                val remoteArtists = artistRemoteDataSource.getTopArtists(limit)
                artistLocalDataSource.deleteArtistNotIn(getValidArtistName())
                insertAll(remoteArtists.toModels())
                dbTrackingDataSource.updateArtistTimestamp(System.currentTimeMillis())
                remoteArtists.toModels()
            } catch (_: Exception) {
                localArtists.toModels()
            }
        }
    }

    override suspend fun getArtistById(artistId: Int): Artist? {
        return artistLocalDataSource.getArtistById(artistId)?.toModel()
    }

    override suspend fun getArtistByName(artistName: String): Artist? {
        val localArtist = artistLocalDataSource.getArtistByName(artistName)
        return localArtist?.toModel() ?: try {
            artistRemoteDataSource.getAllArtists()
                .find { it.name.equals(artistName) }?.toModel()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun insertAll(artists: List<Artist>) {
        val artistEntities = artists.map {
            it.toEntity()
        }
        artistLocalDataSource.insertAll(artistEntities)
    }
}