package com.example.infrastructure.repository

import com.example.core_database.datasource.user.UserFavoriteSongLocalDataSource
import javax.inject.Inject

class FavoriteSongRepositoryImpl @Inject constructor(
    private val favoriteSongLocalDataSource: UserFavoriteSongLocalDataSource,
) {
}