package com.example.core_network.datasource

import com.example.core_network.dto.ArtistDto
import com.example.core_network.dto.ArtistListDto
import com.example.core_network.dto.PagingParamRequest

interface ArtistRemoteDataSource {
    suspend fun loadArtistPaging(param: PagingParamRequest): List<ArtistDto>
    suspend fun getAllArtists(): List<ArtistDto>
    suspend fun getTopArtists(limit: Int): List<ArtistDto>
    suspend fun getArtistByName(artistName: String): ArtistDto?
    suspend fun getArtistById(artistId: Int): ArtistDto?
}