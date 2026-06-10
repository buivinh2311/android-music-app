package com.example.infrastructure.di.user

import com.example.core_domain.repository.RecentSongRepository
import com.example.infrastructure.repository.RecentSongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RecentSongRepositoryModule {
    @Binds
    abstract fun bindRecentSongRepository(
        impl: RecentSongRepositoryImpl
    ): RecentSongRepository
}