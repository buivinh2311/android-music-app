package com.example.feature_favorite.domain.usecase

import com.example.core_domain.repository.FavoriteSongRepository
import com.example.core_model.DisplaySong
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(
    private val repository: FavoriteSongRepository
) {
    suspend operator fun invoke(): List<DisplaySong> {
        return repository.getFavoriteSongs()
    }
}