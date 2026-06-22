package com.example.infrastructure.di.core

import com.example.core_playback.MediaControllerProvider
import com.example.infrastructure.media.MediaControllerHolder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaControllerModule {
    @Binds
    @Singleton
    abstract fun bindMediaControllerProvider(
        impl: MediaControllerHolder
    ): MediaControllerProvider
}