package com.example.infrastructure.media

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.core_playback.MediaControllerProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaControllerHolder @Inject constructor(
    @ApplicationContext context: Context
) : MediaControllerProvider {
    private val _controllerFlow = MutableStateFlow<MediaController?>(null)
    override val controllerFlow: StateFlow<MediaController?> = _controllerFlow.asStateFlow()

    init {
        val sessionToken = SessionToken(
                context,
                ComponentName(
                    context,
                    PlaybackService::class.java
                )
            )

        val future = MediaController.Builder(
                context,
                sessionToken
            ).buildAsync()

        future.addListener({
            _controllerFlow.value = future.get()
        }, ContextCompat.getMainExecutor(context))
    }

    override suspend fun await() = controllerFlow.filterNotNull().first()
}