package com.example.infrastructure.source.song

import android.util.Log
import com.example.core_network.api.SongApi
import com.example.core_network.datasource.SongRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.core_network.dto.SongDto
import com.example.core_network.dto.SongListDto
import com.example.core_network.utils.safeApiCall
import com.example.infrastructure.mapper.remote.toDisplayModels
import javax.inject.Inject

class SongRemoteDataSourceImpl @Inject constructor(
    private val songApi: SongApi
): SongRemoteDataSource {
    override suspend fun loadSongPaging(param: PagingParamRequest): SongListDto {
        return safeApiCall("loadSongPaging") {
            songApi.loadSongPaging(param.offset, param.limit)
        }
    }

    override suspend fun updateCounter(songId: String) {
        safeApiCall("updateCounter") {
            songApi.updateCounter(songId = songId)
        }
    }

    override suspend fun getFirstNSongs(
        playlistId: Int,
        limit: Int
    ): SongListDto {
        return safeApiCall("getFirstNSongs") {
            songApi.getFirstNSongs(playlistId, limit)
        }
    }

    override suspend fun getPreviousNSongs(
        playlistId: Int,
        songId: String,
        limit: Int
    ): SongListDto {
        return safeApiCall("getPreviousNSongs") {
            songApi.getPreviousNSongs(playlistId, songId, limit)
        }
    }

    override suspend fun getNextNSongs(
        playlistId: Int,
        songId: String,
        limit: Int
    ): SongListDto {
        return safeApiCall("getNextNSongs") {
            songApi.getNextNSongs(playlistId, songId, limit)
        }
    }

    override suspend fun getLastNSongs(
        playlistId: Int,
        limit: Int
    ): SongListDto {
        return safeApiCall("getLastNSongs") {
            songApi.getLastNSongs(playlistId, limit)
        }
    }

    override suspend fun getAllSong(): SongListDto {
        return safeApiCall("getAllSong") {
            songApi.getAllSong()
        }
    }

    override suspend fun getSongsByAlbumName(albumName: String): SongListDto {
        return safeApiCall("getSongsByAlbumName") {
            songApi.getSongsByAlbumName(albumName = albumName)
        }
    }

    override suspend fun getSongsByAlbumId(albumId: Int): SongListDto {
        return safeApiCall("getSongByAlbumId") {
            songApi.getSongsByAlbumId(albumId = albumId)
        }
    }

    override suspend fun findSongsBySongNameOrArtistName(query: String): SongListDto {
        return safeApiCall("findSongsBySongNameOrArtistName") {
            songApi.findSongsBySongNameOrArtistName(query = query)
        }
    }

    override suspend fun getSongsByArtistName(artistName: String): List<SongDto> {
        return safeApiCall("getSongsByArtistName") {
            songApi.getAllSong().songListDto.filter { song ->
                song.artist?.split("ft")?.any { artist ->
                    artist.trim().equals(artistName, ignoreCase = true)
                } == true
            }
        }
    }

    override suspend fun getSongsByArtistId(artistId: Int): SongListDto {
        return safeApiCall("getSongsByArtistId") {
            songApi.getSongsByArtistId(artistId)
        }
    }

    override suspend fun getSongById(songId: String): SongDto? {
        return safeApiCall("getSongById") {
            songApi.getSongById(songId = songId)
        }
    }

    override suspend fun getRecommendedSongs(limit: Int): List<SongDto> {
        return safeApiCall("getRecommendedSongs") {
            songApi.getAllSong().songListDto
                .sortedBy { it.counter }
                .take(limit)
        }
    }

    override suspend fun getMostHeardSongs(limit: Int): List<SongDto> {
        return safeApiCall("getMostHeardSongs") {
            songApi.getAllSong().songListDto
                .sortedByDescending { it.counter }
                .take(limit)
        }
    }

    override suspend fun getForYouSongs(limit: Int): List<SongDto> {
        return safeApiCall("getForYouSongs") {
            songApi.getAllSong().songListDto
                .sortedBy { it.id }
                .take(limit)
        }
    }
}