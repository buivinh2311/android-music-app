package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.SearchSongLocalDataSource
import com.example.infrastructure.source.user.SearchSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchSongLocalDataSourceModule {
    @Binds
    abstract fun bindSearchSongLocalDataSource(
        impl: SearchSongLocalDataSourceImpl
    ): SearchSongLocalDataSource
}