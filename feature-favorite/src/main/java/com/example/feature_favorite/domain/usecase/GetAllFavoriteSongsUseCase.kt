package com.example.feature_favorite.domain.usecase

import com.example.core_domain.repository.FavoriteSongRepository
import com.example.core_model.DisplaySong
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(
    private val repository: FavoriteSongRepository
) {
    operator fun invoke(): Flow<List<DisplaySong>> {
        return repository.getFavoriteSongs()
    }
}