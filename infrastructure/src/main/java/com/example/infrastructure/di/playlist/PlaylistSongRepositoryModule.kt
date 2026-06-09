package com.example.infrastructure.di.playlist

import com.example.core_domain.repository.PlaylistSongRepository
import com.example.infrastructure.repository.PlaylistSongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaylistSongRepositoryModule {
    @Binds
    abstract fun bindPlaylistSongRepository(
        impl: PlaylistSongRepositoryImpl
    ): PlaylistSongRepository
}