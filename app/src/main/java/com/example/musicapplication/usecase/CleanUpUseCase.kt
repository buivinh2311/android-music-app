package com.example.musicapplication.usecase

import com.example.core_domain.repository.SongRepository
import javax.inject.Inject

class CleanUpUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke() {
        repository.cleanUp()
    }
}