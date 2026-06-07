package com.example.core_network.dto

import com.google.gson.annotations.SerializedName

data class PagingParamRequest(
    @SerializedName("offset")
    val offset: Int,

    @SerializedName("limit")
    val limit: Int
)
