package com.example.netflixfeo.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.netflixfeo.modelo.Pelicula
import com.example.netflixfeo.modelo.Puntuacion
import com.example.netflixfeo.ui.componentes.EstrellasPuntacion
import com.example.netflixfeo.ui.componentes.MostrarPeli

@Composable
fun MostrarEstadisticasPelicula(
    peliculaSelcionada: Pelicula,
    puntuacionPeliPulsada: Puntuacion,
    actualizarPuntosPuntuacion: (Double) -> Unit
) {

    Column {
        MostrarPeli(peliculaSelcionada = peliculaSelcionada)
        EstrellasPuntacion(puntuacionPeliPulsada, actualizarPuntosPuntuacion)
    }


}