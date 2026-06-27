package com.example.core_domain.usecase

import com.example.core_domain.repository.FavoriteSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteSongUseCases @Inject constructor(
    private val favoriteSongRepository: FavoriteSongRepository
) {
    suspend fun addSongToFavorite(songId: String) {
        favoriteSongRepository.addSongToFavorite(songId)
    }

    fun observeFavoriteSong(songId: String): Flow<Boolean> {
        return favoriteSongRepository.isFavoriteSong(songId)
    }

    suspend fun removeSongFromFavorite(songId: String) {
        favoriteSongRepository.removeSongFromFavorite(songId)
    }
}