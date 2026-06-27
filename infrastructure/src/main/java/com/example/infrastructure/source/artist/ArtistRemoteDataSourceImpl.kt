package com.example.infrastructure.source.artist

import com.example.core_network.api.ArtistApi
import com.example.core_network.datasource.ArtistRemoteDataSource
import com.example.core_network.dto.ArtistDto
import com.example.core_network.dto.PagingParamRequest
import com.example.core_network.utils.safeApiCall
import javax.inject.Inject

class ArtistRemoteDataSourceImpl @Inject constructor(
    private val artistApi: ArtistApi
): ArtistRemoteDataSource {
    override suspend fun loadArtistPaging(param: PagingParamRequest): List<ArtistDto> {
        return safeApiCall("loadArtistPaging") {
            artistApi.loadArtistPaging(param.offset, param.limit).artistListDto
        }
    }

    override suspend fun getAllArtists(): List<ArtistDto> {
        return safeApiCall("getAllArtist") {
            artistApi.getAllArtist().artistListDto
        }
    }

    override suspend fun getTopArtists(limit: Int): List<ArtistDto> {
        return safeApiCall("getAllArtist") {
            artistApi.getAllArtist().artistListDto
                .sortedWith(
                    compareByDescending<ArtistDto> { it.interested }
                        .thenBy { it.id }
                )
                .take(limit)
        }
    }

    override suspend fun getArtistByName(artistName: String): ArtistDto {
        return safeApiCall("getArtistByName") {
            artistApi.getArtistByName(query = artistName).artistListDto[0]
        }
    }

    override suspend fun getArtistById(artistId: Int): ArtistDto {
        return safeApiCall("getArtistById") {
            artistApi.getArtistById(artistId = artistId)
        }
    }
}