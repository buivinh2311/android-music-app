package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.DownloadSongLocalDataSource
import com.example.infrastructure.source.user.DownloadSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadSongLocalDataSourceModule {
    @Binds
    abstract fun bindDownloadSongLocalDataSource(
        impl: DownloadSongLocalDataSourceImpl
    ): DownloadSongLocalDataSource
}