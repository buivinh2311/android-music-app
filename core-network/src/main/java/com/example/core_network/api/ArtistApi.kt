package com.example.core_network.api

import com.example.core_network.dto.ArtistDto
import com.example.core_network.dto.ArtistListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistApi {

    @GET("/services/services.php/artists")
    suspend fun loadArtistPaging(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ArtistListDto

    @GET("/services/services.php?queryType=allArtists")
    suspend fun getAllArtist(): ArtistListDto

    @GET("services/services.php")
    suspend fun getArtistByName(
        @Query("queryType") queryType: String = "searchArtist",
        @Query("query") query: String
    ): ArtistListDto

    @GET("/services/services.php")
    suspend fun getArtistById(
        @Query("queryType") queryType: String = "artist",
        @Query("artistId") artistId: Int
    ): ArtistDto
}