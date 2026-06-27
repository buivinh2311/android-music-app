package com.example.feature_favorite.usecase

import com.example.core_domain.repository.FavoriteSongRepository
import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(
    private val repository: FavoriteSongRepository
) {
    operator fun invoke(): Flow<List<Song>> {
        return repository.getFavoriteSongs()
    }
}