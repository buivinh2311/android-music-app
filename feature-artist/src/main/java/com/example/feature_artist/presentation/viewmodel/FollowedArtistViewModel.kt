package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Artist
import com.example.feature_artist.domain.usecase.GetFavoriteArtistUseCase
import com.example.feature_artist.domain.usecase.ObserverFavoriteArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowedArtistViewModel @Inject constructor(
    getFavoriteArtistUseCase: GetFavoriteArtistUseCase
): ViewModel() {
    val followedArtists = getFavoriteArtistUseCase()
}