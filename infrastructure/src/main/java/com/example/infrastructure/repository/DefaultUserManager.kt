package com.example.infrastructure.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.core_domain.manager.UserManager
import com.example.core_model.User
import javax.inject.Inject

class DefaultUserManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
): UserManager {
    override fun isLoggedIn(): Boolean {
        return getCurrentUserId() != 0
    }

    override fun getCurrentUserId(): Int {
        return sharedPreferences.getInt(PREF_USER_ID, 0)
    }

    override fun getCurrentUser(): User? {
        val userId = getCurrentUserId()
        return if(userId == -1) null else User(userId, "", "", "")
    }

    override fun login(user: User) {
        sharedPreferences.edit { putInt(PREF_USER_ID, user.id) }
    }

    override fun logout() {
        sharedPreferences.edit { putInt(PREF_USER_ID, 0) }
    }

    companion object {
        const val PREF_USER_ID = "pref_user_id"
    }

}