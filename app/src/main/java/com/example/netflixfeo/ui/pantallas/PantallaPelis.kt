package com.example.netflixfeo.ui.pantallas

import android.graphics.Color.rgb
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.netflixfeo.modelo.Pelicula
import com.example.netflixfeo.ui.componentes.CaratulaPeli
import com.example.netflixfeo.ui.componentes.MostrarPeli

@Composable
fun MostrarPelis(peliculas: List<Pelicula>,
                 onClickPeli: (Pelicula) -> Unit,
                 peliculaSelcionada: Pelicula,
                 onClickEstadistica: () -> Unit,
                 onClickReproducir: () -> Unit
) {
   Column(modifier = Modifier.padding(10.dp)) {
      LazyRow {
         items(peliculas) { peli ->
            CaratulaPeli(peli, onClickPeli = onClickPeli)
         }
      }
      Row {
         MostrarPeli(peliculaSelcionada)
         Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
         ) {
            BTNEstadisticas(onClickEstadistica)
            BTNReproducir(onClickReproducir)
         }
      }
   }

}

@Composable
private fun BTNEstadisticas(onClickEstadistica: () -> Unit) {
   FilledIconButton(
      onClick = onClickEstadistica,
      shape = CircleShape,
      modifier = Modifier
         .fillMaxWidth()
         .padding(10.dp),
      colors = IconButtonColors(
         contentColor = Color(rgb(238, 223, 122)),
         disabledContentColor = Color.Yellow,
         disabledContainerColor = Color.Red,
         containerColor = Color(rgb(160, 71, 71))
      )
   ) {
      Icon(
         contentDescription = "Boton de Informacion",
         imageVector = Icons.Filled.Info,
      )
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BTNReproducir(onClickReproducir: () -> Unit) {
   FilledIconButton(
      onClick = onClickReproducir,
      shape = CircleShape,
      modifier = Modifier
         .fillMaxWidth()
         .padding(10.dp),
      colors = IconButtonColors(
         contentColor = Color(rgb(160, 71, 71)),
         disabledContentColor = Color.Yellow,
         disabledContainerColor = Color.Red,
         containerColor = Color(rgb(238, 223, 122))
      )
   ) {
      Icon(
         contentDescription = "Boton de reproducir",
         imageVector = Icons.Filled.PlayArrow,
      )
   }
}

