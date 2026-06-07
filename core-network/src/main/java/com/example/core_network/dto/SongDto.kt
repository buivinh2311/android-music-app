package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

data class SongDto(
    @SerializedName("id")
    val id: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("album")
    val album: String?,

    @SerializedName("artist")
    val artist: String?,

    @SerializedName("source")
    val sourceUrl: String? = null,

    @SerializedName("image")
    val artworkUrl: String? = null,

    @SerializedName("duration")
    val duration: Int?,

    @SerializedName("favorite")
    val favorite: Int?,

    @SerializedName("counter")
    val counter: Int?,

    @SerializedName("replay")
    val replay: Int?
)
