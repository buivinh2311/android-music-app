package com.example.infrastructure.repository

import android.util.Log
import com.example.core_database.datasource.artist.ArtistLocalDataSource
import com.example.core_domain.repository.ArtistRepository
import com.example.core_model.Artist
import com.example.core_network.datasource.ArtistRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.local.toModels
import com.example.infrastructure.mapper.remote.toModel
import com.example.infrastructure.mapper.remote.toModels
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistRemoteDataSource: ArtistRemoteDataSource
): ArtistRepository {
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
        return if(localArtists.size >= limit) {
            localArtists.toModels()
        } else {
            try {
                val remoteArtists = artistRemoteDataSource.getTopArtists(limit)
                insertAll(remoteArtists.toModels())
                remoteArtists.toModels()
            } catch (_: Exception) {
                emptyList()
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