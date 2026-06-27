package com.example.core_network.api

import com.example.core_network.dto.SongDto
import com.example.core_network.dto.SongListDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SongApi {
    @GET("/services/services.php/songs")
    suspend fun loadSongPaging(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): SongListDto

    @POST("/services/services.php")
    suspend fun updateCounter(
        @Query("queryType") queryType: String = "updateCounter",
        @Query("songId") songId: String
    )

    @GET("/services/services.php?queryType=allSongs")
    suspend fun getAllSong(): SongListDto

    @GET("/services/services.php/search")
    suspend fun search(
        @Query("query") query: String
    ): SongListDto

    @GET("/services/services.php/firstNSongs")
    suspend fun getFirstNSongs(
        @Query("playlistId") playlistId: Int,
        @Query("limit") limit: Int = 10
    ): SongListDto

    @GET("/services/services.php/previous")
    suspend fun getPreviousNSongs(
        @Query("playlistId") playlistId: Int,
        @Query("songId") songId: String,
        @Query("limit") limit: Int = 10
    ): SongListDto

    @GET("/services/services.php/next")
    suspend fun getNextNSongs(
        @Query("playlistId") playlistId: Int,
        @Query("songId") songId: String,
        @Query("limit") limit: Int = 10
    ): SongListDto

    @GET("/services/services.php/lastNSongs")
    suspend fun getLastNSongs(
        @Query("playlistId") playlistId: Int,
        @Query("limit") limit: Int = 10
    ): SongListDto

    @GET("/services/services.php")
    suspend fun getSongsByAlbumName(
        @Query("queryType") queryType: String = "albumWithSongs",
        @Query("albumName") albumName: String
    ): SongListDto

    @GET("/services/services.php")
    suspend fun getSongsByAlbumId(
        @Query("queryType") queryType: String = "albumWithSongs",
        @Query("albumId") albumId: Int
    ): SongListDto

    @GET("/services/services.php")
    suspend fun findSongsBySongNameOrArtistName(
        @Query("queryType") queryType: String = "search",
        @Query("query") query: String
    ): SongListDto

    @GET("/services/services.php")
    suspend fun getSongsByArtistName(
        @Query("queryType") queryType: String = "artistWithSongs",
        @Query("artistName") artistName: String
    ): SongListDto

    @GET("/services/services.php")
    suspend fun getSongById(
        @Query("queryType") queryType: String = "song",
        @Query("songId") songId: String
    ): SongDto

    @GET("services/services.php/artists/{artistId}/songs")
    suspend fun getSongsByArtistId(
        @Path("artistId") artistId: Int
    ): SongListDto


    @GET("/services/services.php/recentSongs")
    fun getRecentSongs(
        @Query("userId") userId: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 10
    ): SongListDto

    @PUT("/services/services.php/recentSongs")
    suspend fun setRecentSong(
        @Query("userId") userId: Int,
        @Query("songId") songId: String,
        @Query("createdAt") createdAt: Long
    )
}