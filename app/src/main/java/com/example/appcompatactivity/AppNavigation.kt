package com.example.appcompatactivity.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appcompatactivity.ui.screens.AgregarGastoScreen
import com.example.appcompatactivity.ui.screens.ConfiguracionScreen // Import clave
import com.example.appcompatactivity.ui.screens.DetallesScreen
import com.example.appcompatactivity.viewmodel.GastoViewModel
import com.example.appcompatactivity.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()
    val gastoViewModel: GastoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "detalles"
    ) {
        composable("detalles") {
            DetallesScreen(navController, gastoViewModel)
        }
        composable("agregar_gasto") {
            AgregarGastoScreen(navController, gastoViewModel)
        }
        composable("configuracion") {
            ConfiguracionScreen(navController, settingsViewModel)
        }
    }
}
