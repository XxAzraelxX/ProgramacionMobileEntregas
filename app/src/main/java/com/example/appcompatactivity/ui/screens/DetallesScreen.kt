package com.example.appcompatactivity.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcompatactivity.R
import com.example.appcompatactivity.viewmodel.GastoViewModel

data class Gasto(
    val dia: String,
    val descripcion: String,
    val monto: Int,
    val iconoResId: Int
)

@Composable
fun DetallesScreen(navController: NavController, viewModel: GastoViewModel) {
    val gastos = viewModel.gastos
    val nombreUsuario = "usuario@demo.com"

    val subtotal = gastos.sumOf { it.monto }
    val impuesto = (subtotal * 0.1).toInt()
    val total = subtotal + impuesto
    val saldoDisponible = 250000 - total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Hola, $nombreUsuario", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors()
        ) {
            Column(Modifier.padding(12.dp)) {
                Text("Saldo disponible", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("$$saldoDisponible", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(gastos) { gasto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = gasto.iconoResId),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Semana: ${gasto.dia}", fontWeight = FontWeight.Bold)
                        Text(gasto.descripcion, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text("$$${gasto.monto}", fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal (${gastos.size})")
                Text("$$subtotal")
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Impuesto")
                Text("$$impuesto")
            }
            Divider()
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", fontWeight = FontWeight.Bold)
                Text("$$total", fontWeight = FontWeight.Bold)
            }
        }

        Button(
            onClick = { navController.navigate("agregar_gasto") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Agregar Gasto")
        }

        Button(
            onClick = { navController.navigate("configuracion") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Ir a Configuración")
        }
    }
}
