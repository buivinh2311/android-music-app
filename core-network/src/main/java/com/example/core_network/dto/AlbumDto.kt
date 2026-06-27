package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("artwork")
    val artworkUrl: String?,

    @SerializedName("size")
    val size: Int? = 0
)
