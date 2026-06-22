package com.example.infrastructure.di.core

import com.example.core_playback.MediaControllerProvider
import com.example.core_playback.MediaPlaybackController
import com.example.core_playback.PlaybackStateDataSource
import com.example.core_playback.usecase.AddRecentSongUseCase
import com.example.core_playback.usecase.GetSongByIdUseCase
import com.example.core_playback.usecase.IncreasePlayCountUseCase
import com.example.core_playback.usecase.RestorePlaybackQueueUseCase
import com.example.infrastructure.media.MediaPlaybackControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaPlaybackModule {
    @Provides
    @Singleton
    fun provideMediaPlaybackController(
        playbackStateDataSource: PlaybackStateDataSource,
        restorePlaybackQueueUseCase: RestorePlaybackQueueUseCase,
        addRecentSongUseCase: AddRecentSongUseCase,
        increasePlayCountUseCase: IncreasePlayCountUseCase,
        getSongByIdUseCase: GetSongByIdUseCase,
        mediaControllerProvider: MediaControllerProvider
    ): MediaPlaybackController {
        return MediaPlaybackControllerImpl(
            playbackStateDataSource,
            restorePlaybackQueueUseCase,
            addRecentSongUseCase,
            increasePlayCountUseCase,
            getSongByIdUseCase,
            mediaControllerProvider
        )
    }
}