package com.example.feature_settings.di

import com.example.feature_settings.domain.repository.ThemeRepository
import com.example.feature_settings.domain.repository.ThemeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ThemeRepositoryModule {
    @Binds
    abstract fun bindThemeRepository(
        impl: ThemeRepositoryImpl
    ): ThemeRepository
}