package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

data class AlbumListDto(
    @SerializedName("albums")
    val albumListDto: List<AlbumDto> = emptyList()
)
