package com.example.core_playback

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//@AndroidEntryPoint
//class PlaybackService : Service() {
//    @Inject
//    lateinit var playbackController: PlaybackController
//    override fun onBind(intent: Intent?): IBinder? = null
//
//    override fun onCreate() {
//        super.onCreate()
//        createNotificationChannel()
//        startForeground(
//            NOTIFICATION_ID,
//            createNotification()
//        )
//    }
//
//    private fun createNotificationChannel() {
//        val channel = NotificationChannel(
//            CHANNEL_ID,
//            "Playback",
//            NotificationManager.IMPORTANCE_LOW
//        )
//
//        val manager = getSystemService(
//            NotificationManager::class.java
//        )
//
//        manager.createNotificationChannel(channel)
//    }
//
//    private fun createNotification(): Notification {
//        val intent = Intent(
//            this,
//            MainActivity::class.java
//        )
//
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
//        return NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Music Player")
//            .setContentText("Đang phát nhạc")
//            .build()
//    }
//
//    companion object {
//        const val CHANNEL_ID = "playback_channel"
//        const val NOTIFICATION_ID = 1
//    }
//}