package com.example.infrastructure.di.playlist

import com.example.core_domain.repository.PlaylistRepository
import com.example.infrastructure.repository.PlaylistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaylistRepositoryModule {
    @Binds
    abstract fun bindPlaylistRepository(
        impl: PlaylistRepositoryImpl
    ): PlaylistRepository
}