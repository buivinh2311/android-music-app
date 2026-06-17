package com.example.core_playback.di

import android.content.Context
import com.example.core_playback.PlaybackController
import com.example.core_playback.PlaybackControllerImpl
import com.example.core_playback.PlaybackStateDataSource
import com.example.core_playback.usecase.RestorePlaybackQueueUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlaybackModule {
    @Provides
    @Singleton
    fun providePlaybackController(
        @ApplicationContext context: Context,
        playbackStateDataSource: PlaybackStateDataSource,
        restorePlaybackQueueUseCase: RestorePlaybackQueueUseCase
    ): PlaybackController {
        return PlaybackControllerImpl(
            context,
            playbackStateDataSource,
            restorePlaybackQueueUseCase
        )
    }
}