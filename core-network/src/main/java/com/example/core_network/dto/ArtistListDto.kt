package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

class ArtistListDto {
    @SerializedName("artists")
    val artistListDto: List<ArtistDto> = emptyList()
}