package com.example.core_navigation.navigator

interface HomeNavigator {
    fun navigateToAlbum()
    fun navigateToAlbumDetail(albumId: String)
    fun navigateToRecommended()
}