package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

data class SongListDto (
    @SerializedName("songs")
    val songListDto: List<SongDto> = emptyList()
)