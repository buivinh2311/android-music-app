package com.example.core_playback.di

import com.example.core_playback.PlaybackStateDataSource
import com.example.core_playback.PlaybackStateDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaybackStateDataSourceModule {
    @Binds
    abstract fun bindPlaybackStateDataSource(
        impl: PlaybackStateDataSourceImpl
    ): PlaybackStateDataSource
}