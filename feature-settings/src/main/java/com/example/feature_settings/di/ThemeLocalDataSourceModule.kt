package com.example.feature_settings.di

import android.content.Context
import com.example.feature_settings.source.ThemeLocalDataSource
import com.example.feature_settings.source.ThemeLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ThemeLocalDataSourceModule {
    @Provides
    fun provideThemeLocalDataSource(
        @ApplicationContext context: Context
    ): ThemeLocalDataSource {
        return ThemeLocalDataSourceImpl(context)
    }
}