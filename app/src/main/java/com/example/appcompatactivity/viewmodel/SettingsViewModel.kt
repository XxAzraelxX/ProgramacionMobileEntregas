package com.example.appcompatactivity.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val PREFS_NAME = "settings_prefs"
        private const val KEY_BACKGROUND_COLOR = "background_color"
        private const val KEY_DARK_THEME = "dark_theme"
        private const val DEFAULT_COLOR = 0xFFFFFFFF.toInt() // Blanco
    }

    private val prefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _backgroundColor = mutableStateOf(
        prefs.getInt(KEY_BACKGROUND_COLOR, DEFAULT_COLOR)
    )
    val backgroundColor: State<Int> = _backgroundColor

    private val _isDarkTheme = mutableStateOf(
        prefs.getBoolean(KEY_DARK_THEME, false)
    )
    val isDarkTheme: State<Boolean> = _isDarkTheme

    fun setBackgroundColor(color: Int) {
        _backgroundColor.value = color
        prefs.edit().putInt(KEY_BACKGROUND_COLOR, color).apply()
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        prefs.edit().putBoolean(KEY_DARK_THEME, isDark).apply()
    }
}
