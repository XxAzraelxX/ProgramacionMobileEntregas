package com.example.appcompatactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcompatactivity.navigation.AppNavigation
import com.example.appcompatactivity.ui.theme.AppCompatActivityTheme
import com.example.appcompatactivity.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme
            val backgroundColor by settingsViewModel.backgroundColor

            AppCompatActivityTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.background(Color(backgroundColor))
                ) {
                    AppNavigation(settingsViewModel)
                }
            }
        }
    }
}
