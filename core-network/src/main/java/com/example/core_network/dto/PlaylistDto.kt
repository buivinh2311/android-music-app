package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

data class PlaylistDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("songs")
    val songs: List<String>?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("artwork")
    val artworkUrl: String? = null
)


