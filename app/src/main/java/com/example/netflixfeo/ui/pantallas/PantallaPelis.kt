package com.example.netflixfeo.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.netflixfeo.modelo.Pelicula
import com.example.netflixfeo.ui.componentes.CaratulaPeli

@Composable
fun MostrarPelis(
        peliculas : List<Pelicula> ,
        onClickPeli : (Pelicula) -> Unit ,
        peliculaSelcionada : Pelicula
                ) {
    Column(modifier = Modifier.padding(10.dp)) {
        LazyRow {
            items(peliculas) { peli ->
                CaratulaPeli(peli , onClickPeli = onClickPeli)
            }
        }
        MostrarPeli(peliculaSelcionada)
    }

}

@Composable
fun MostrarPeli(peliculaSelcionada : Pelicula) {
    CaratulaPeli(peliculaSelcionada , 400.dp , 300.dp)
    Text(text = "Titulo: " + peliculaSelcionada.nombre)

    Text(text = "Actor principal " + peliculaSelcionada.actorPrincipal)
    Text(text = "Duracion: " + peliculaSelcionada.duracionMinutos)
    Text(text = "Director: " + peliculaSelcionada.director)
}