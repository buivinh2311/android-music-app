package com.example.core_network.api

import com.example.core_network.dto.AlbumDto
import com.example.core_network.dto.AlbumListDto
import com.example.core_network.dto.SongListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApi {

    @GET("/services/services.php/albums")
    suspend fun loadAlbumPaging(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): AlbumListDto

    @GET("/services/services.php?queryType=allAlbums")
    suspend fun getAllAlbum(): AlbumListDto

    @GET("/services/services.php/albums/{albumId}")
    suspend fun getAlbumById(@Path("albumId") albumId: Int): AlbumDto
}