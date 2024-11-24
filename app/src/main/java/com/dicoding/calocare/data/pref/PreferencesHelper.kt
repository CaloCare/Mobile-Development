package com.dicoding.calocare.data.pref

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun setDarkMode(enabled: Boolean) {
        preferences.edit().putBoolean(DARK_MODE_KEY, enabled).apply()
    }

    fun isDarkMode(): Boolean {
        return preferences.getBoolean(DARK_MODE_KEY, false)
    }

    companion object {
        private const val DARK_MODE_KEY = "dark_mode"
    }
}