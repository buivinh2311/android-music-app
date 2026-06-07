package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.UserDownloadSongLocalDataSource
import com.example.infrastructure.source.user.UserDownloadSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDownloadSongLocalDataSourceModule {
    @Binds
    abstract fun bindUserDownloadSongLocalDataSource(
        impl: UserDownloadSongLocalDataSourceImpl
    ): UserDownloadSongLocalDataSource
}