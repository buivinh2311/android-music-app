package com.example.feature_playlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.feature_playlist.usecase.GetLimitPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    getLimitPlaylistUseCase: GetLimitPlaylistUseCase
): ViewModel() {
    val playlist = getLimitPlaylistUseCase(1000)
}