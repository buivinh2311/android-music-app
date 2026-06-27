package com.example.core_database.datasource.user

import com.example.core_model.download.DownloadInfo

interface DownloadManagerLocalDataSource {
    fun enqueue(songId: String, title: String, sourceUrl: String): Long
    fun remove(downloadId: Long)
    fun query(downloadId: Long): DownloadInfo
}