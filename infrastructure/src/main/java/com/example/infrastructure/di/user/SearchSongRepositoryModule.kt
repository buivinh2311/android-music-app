package com.example.infrastructure.di.user

import com.example.core_domain.repository.SearchSongRepository
import com.example.infrastructure.repository.SearchSongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchSongRepositoryModule {
    @Binds
    abstract fun bindSearchSongRepository(
        impl: SearchSongRepositoryImpl
    ): SearchSongRepository
}