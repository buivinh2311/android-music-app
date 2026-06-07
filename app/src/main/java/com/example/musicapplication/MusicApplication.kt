package com.example.musicapplication

import android.app.Application
import androidx.core.content.edit
import com.example.core_domain.usecase.RegisterUseCase
import com.example.core_model.User
import com.example.core_utils.util.AppUtil
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MusicApplication: Application() {
    @Inject
    lateinit var registerUseCase: RegisterUseCase

    private fun setupDefaultUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val defaultUser = User(id = 0, username = "guest")
            val isSuccess = registerUseCase(defaultUser)
            if(isSuccess) {
                val sharedPreferences = getSharedPreferences(AppUtil.PREF_FILE_NAME, MODE_PRIVATE)
                sharedPreferences.edit {
                    putInt(AppUtil.PREF_CURRENT_USER_ID, defaultUser.id)
                }
            }
        }
    }
}