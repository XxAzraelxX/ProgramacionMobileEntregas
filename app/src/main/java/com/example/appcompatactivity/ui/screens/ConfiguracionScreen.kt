package com.example.appcompatactivity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcompatactivity.viewmodel.SettingsViewModel

@Composable
fun ConfiguracionScreen(navController: NavController, settingsViewModel: SettingsViewModel) {

    val isDarkTheme by settingsViewModel.isDarkTheme
    val backgroundColor by settingsViewModel.backgroundColor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Configuración", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 24.dp))

        // Cambiar tema claro/oscuro
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = !isDarkTheme, onClick = { settingsViewModel.setDarkTheme(false) })
            Text("Modo Claro", modifier = Modifier.clickable { settingsViewModel.setDarkTheme(false) })
            Spacer(Modifier.width(16.dp))
            RadioButton(selected = isDarkTheme, onClick = { settingsViewModel.setDarkTheme(true) })
            Text("Modo Oscuro", modifier = Modifier.clickable { settingsViewModel.setDarkTheme(true) })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Selecciona el color de fondo:", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val colors = listOf(Color.White, Color(0xFFBBDEFB), Color(0xFFC8E6C9), Color(0xFFFFCDD2))

            colors.forEach { color ->
                Box(modifier = Modifier
                    .size(40.dp)
                    .background(color)
                    .clickable { settingsViewModel.setBackgroundColor(color.toArgb()) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Guardar y volver")
        }
    }
}
