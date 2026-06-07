package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("interested")
    val interested: Int?
)
