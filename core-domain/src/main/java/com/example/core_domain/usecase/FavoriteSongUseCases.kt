package com.example.core_domain.usecase

import android.util.Log
import com.example.core_domain.repository.FavoriteSongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavoriteSongUseCases @Inject constructor(
    private val favoriteSongRepository: FavoriteSongRepository
) {
    suspend fun addSongToFavorite(songId: String) {
        favoriteSongRepository.addSongToFavorite(songId)
    }

    fun observerFavoriteSong(songId: String): Flow<Boolean> {
        return favoriteSongRepository.isFavoriteSong(songId)
    }

    suspend fun removeSongFromFavorite(songId: String) {
        favoriteSongRepository.removeSongFromFavorite(songId)
    }
}