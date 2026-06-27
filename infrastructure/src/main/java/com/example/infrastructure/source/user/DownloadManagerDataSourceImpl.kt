package com.example.infrastructure.source.user

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.core_database.datasource.user.DownloadManagerLocalDataSource
import com.example.core_model.download.DownloadInfo
import com.example.core_model.download.DownloadState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DownloadManagerDataSourceImpl @Inject constructor (
    @param:ApplicationContext private val context: Context
) : DownloadManagerLocalDataSource {

    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    private fun mapStatus(status: Int): DownloadState {
        return when(status) {
            DownloadManager.STATUS_PENDING, DownloadManager.STATUS_RUNNING,
            DownloadManager.STATUS_PAUSED -> {
                DownloadState.DOWNLOADING
            }
            DownloadManager.STATUS_SUCCESSFUL -> DownloadState.SUCCESSFUL
            DownloadManager.STATUS_FAILED -> DownloadState.FAILED
            else -> DownloadState.FAILED
        }
    }

    override fun enqueue(
        songId: String,
        title: String,
        sourceUrl: String
    ): Long {
        val request = DownloadManager.Request(sourceUrl.toUri())
            .setTitle(title)
            .setDescription("Downloading...")
            .setMimeType("audio/mpeg")
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_MUSIC,
                "${songId}.mp3"
            )
        return downloadManager.enqueue(request)
    }

    override fun remove(downloadId: Long) {
        downloadManager.remove(downloadId)
    }

    override fun query(downloadId: Long): DownloadInfo {
        val query = DownloadManager.Query()
            .setFilterById(downloadId)

        val cursor = downloadManager.query(query)

        cursor.use {

            if (!it.moveToFirst()) {
                return DownloadInfo(
                    state = mapStatus(DownloadManager.STATUS_FAILED),
                    localUri = null,
                    reason = -1
                )
            }

            val status = it.getInt(
                it.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)
            )

            val localUri = it.getString(
                it.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI)
            )

            val reason = it.getInt(
                it.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON)
            )

            return DownloadInfo(
                state = mapStatus(status),
                localUri = localUri,
                reason = reason
            )
        }
    }
}