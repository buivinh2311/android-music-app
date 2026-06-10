package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.RecentSongLocalDataSource
import com.example.infrastructure.source.user.RecentSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RecentSongLocalDataSourceModule {
    @Binds
    abstract fun bindRecentSongLocalDataSource(
        impl: RecentSongLocalDataSourceImpl
    ): RecentSongLocalDataSource
}