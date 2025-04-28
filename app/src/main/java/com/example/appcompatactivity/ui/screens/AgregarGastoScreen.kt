package com.example.appcompatactivity.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcompatactivity.R
import com.example.appcompatactivity.viewmodel.GastoViewModel


@Composable
fun AgregarGastoScreen(navController: NavController, viewModel: GastoViewModel) {
    var dia by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var iconoSeleccionado by remember { mutableStateOf(R.drawable.ic_bus) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Nuevo Gasto", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = dia,
            onValueChange = { dia = it },
            label = { Text("Día de la semana") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text("Selecciona tipo de gasto", style = MaterialTheme.typography.labelLarge)

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            IconToggleButton(
                checked = iconoSeleccionado == R.drawable.ic_bus,
                onCheckedChange = { iconoSeleccionado = R.drawable.ic_bus }
            ) {
                Icon(painter = painterResource(R.drawable.ic_bus), contentDescription = "Transporte")
            }
            IconToggleButton(
                checked = iconoSeleccionado == R.drawable.ic_food,
                onCheckedChange = { iconoSeleccionado = R.drawable.ic_food }
            ) {
                Icon(painter = painterResource(R.drawable.ic_food), contentDescription = "Comida")
            }
            IconToggleButton(
                checked = iconoSeleccionado == R.drawable.ic_movie,
                onCheckedChange = { iconoSeleccionado = R.drawable.ic_movie }
            ) {
                Icon(painter = painterResource(R.drawable.ic_movie), contentDescription = "Ocio")
            }
        }

        Button(
            onClick = {
                val gasto = Gasto(
                    dia = dia,
                    descripcion = descripcion,
                    monto = monto.toIntOrNull() ?: 0,
                    iconoResId = iconoSeleccionado
                )
                viewModel.agregarGasto(gasto)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
