package com.example.infrastructure.di.user

import com.example.core_domain.repository.DownloadSongRepository
import com.example.infrastructure.repository.DownloadSongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadSongRepositoryModule {
    @Binds
    abstract fun bindDownloadSongRepository(
        impl: DownloadSongRepositoryImpl
    ): DownloadSongRepository
}