package com.example.appcompatactivity.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.appcompatactivity.R
import com.example.appcompatactivity.ui.screens.Gasto

class GastoViewModel : ViewModel() {
    val gastos = mutableStateListOf(
        Gasto("Lunes", "Casa a trabajo", 12000, R.drawable.ic_bus),
        Gasto("Martes", "Salida a cine", 15000, R.drawable.ic_movie)
    )

    fun agregarGasto(gasto: Gasto) {
        gastos.add(gasto)
    }
}
