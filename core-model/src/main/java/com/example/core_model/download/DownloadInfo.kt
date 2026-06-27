package com.example.core_model.download

data class DownloadInfo(
    val state: DownloadState,
    val localUri: String?,
    val reason: Int
)