package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.DownloadManagerLocalDataSource
import com.example.infrastructure.source.user.DownloadManagerDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadManagerDataSourceModule {
    @Binds
    abstract fun bindDownloadManagerDataSource(
        impl: DownloadManagerDataSourceImpl
    ): DownloadManagerLocalDataSource
}