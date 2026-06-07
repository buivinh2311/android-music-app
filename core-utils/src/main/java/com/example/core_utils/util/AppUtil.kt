package com.example.core_utils.util

import com.example.core_model.Playlist

object AppUtil {
    const val EXTRA_CURRENT_FRACTION = "EXTRA_CURRENT_FRACTION"
    const val EXTRA_NETWORK_STATE = "EXTRA_NETWORK_STATE"
    const val PREF_SONG_ID = "PREF_SONG_ID"
    const val PREF_CURRENT_POSITION = "PREF_CURRENT_POSITION"
    const val PREF_PLAYLIST_ID = "PREF_PLAYLIST_ID"
    const val PREF_FILE_NAME = "music_app_pref"
    const val PREF_CURRENT_USER_ID = "PREF_CURRENT_USER_ID"
    const val SECTION_PAGE_SIZE = 12
    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_LIST_SIZE = 40
    const val CACHE_TIMEOUT = 168L

    val defaultPlaylists = mapOf(
        0L to Playlist(id = 0, name = DefaultPlaylistName.DEFAULT.value),
        1L to Playlist(id = 1, name = DefaultPlaylistName.RECOMMENDED.value),
        2L to Playlist(id = 2, name = DefaultPlaylistName.FAVORITES.value),
        3L to Playlist(id = 3, name = DefaultPlaylistName.RECENT.value),
        4L to Playlist(id = 4, name = DefaultPlaylistName.MOST_HEARD.value),
        5L to Playlist(id = 5, name = DefaultPlaylistName.FOR_YOU.value),
        6L to Playlist(id = 6, name = DefaultPlaylistName.SEARCH.value),
        7L to Playlist(id = 7, name = DefaultPlaylistName.HISTORY_SEARCH.value),
        8L to Playlist(id = 8, name = DefaultPlaylistName.CUSTOM.value),
        9L to Playlist(id = 9, name = DefaultPlaylistName.MORE_RECOMMENDED.value),
        10L to Playlist(id = 10, name = DefaultPlaylistName.MORE_FAVORITE.value),
    )

    enum class DefaultPlaylistName(val value: String) {
        DEFAULT("Default"),
        FAVORITES("Favorites"),
        MORE_FAVORITE("More Favorites"),
        RECOMMENDED("Recommended"),
        MORE_RECOMMENDED("More Recommended"),
        RECENT("Recent"),
        SEARCH("Search"),
        MOST_HEARD("Most_Heard"),
        FOR_YOU("For_You"),
        HISTORY_SEARCH("history_searched"),
        CUSTOM("Custom")
    }
}