package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

class PlaylistListDto {
    @SerializedName("playlists")
    val playlistListDto: List<PlaylistDto> = emptyList()
}