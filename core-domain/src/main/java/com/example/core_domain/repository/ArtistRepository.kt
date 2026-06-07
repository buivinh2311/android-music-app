package com.example.core_domain.repository

import com.example.core_model.Artist
import com.example.core_network.dto.PagingParamRequest

interface ArtistRepository {
    suspend fun loadArtistPaging(param: PagingParamRequest): List<Artist>
    suspend fun getAllArtists(): List<Artist>
    suspend fun getTopArtists(limit: Int): List<Artist>
    suspend fun getArtistById(artistId: Int): Artist?
    suspend fun getArtistByName(artistName: String): Artist?
    suspend fun insertAll(artists: List<Artist>)
}