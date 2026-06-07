package com.example.infrastructure.di.song

import com.example.core_domain.repository.SongRepository
import com.example.infrastructure.repository.SongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SongRepositoryModule {
    @Binds
    abstract fun bindSongRepository(
        impl: SongRepositoryImpl
    ): SongRepository
}