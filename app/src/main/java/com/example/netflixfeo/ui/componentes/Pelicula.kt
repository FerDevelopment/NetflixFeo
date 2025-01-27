package com.example.netflixfeo.ui.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.netflixfeo.R
import com.example.netflixfeo.modelo.Pelicula

@Composable
fun CaratulaPeli(pelicula: Pelicula, onClickPeli: (Pelicula) -> Unit) {
    Card(modifier = Modifier.padding(8.dp), onClick = { onClickPeli(pelicula) }) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(pelicula.caratula)
                .crossfade(true).build(),
            error = painterResource(id = R.drawable.error),
            placeholder = painterResource(id = R.drawable.cargando),
            contentDescription = stringResource(R.string.imagen_peli),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RectangleShape)
                .height(200.dp)
                .width(150.dp)
        )
    }
}

@Composable
fun CaratulaPeli(pelicula: Pelicula, alto: Dp = 200.dp, ancho: Dp = 150.dp) {
    Card(
        modifier = Modifier.padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(pelicula.caratula)
                .crossfade(true).build(),
            error = painterResource(id = R.drawable.error),
            placeholder = painterResource(id = R.drawable.cargando),
            contentDescription = stringResource(R.string.imagen_peli),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RectangleShape)
                .height(alto)
                .width(ancho)
        )
    }
}

@Composable
fun MostrarPeli(peliculaSelcionada: Pelicula) {
    Column {
        CaratulaPeli(peliculaSelcionada, 350.dp, 250.dp)
        Text(text = "Titulo: " + peliculaSelcionada.nombre)
        Text(text = "Actor principal " + peliculaSelcionada.actorPrincipal)
        Text(text = "Duracion: " + peliculaSelcionada.duracionMinutos)
        Text(text = "Director: " + peliculaSelcionada.director)
    }

}

@Composable
fun VerPelicula(onVerPelicula: () -> Unit, onCambiarDialogo: () -> Unit, ver: Boolean) {

    if (ver) {
        AlertDialog(title = { Text("Ver pelicula") },
            text = { Text("¿Desea ver la pelicula, mi reina/rey?") },
            confirmButton = {
                Button(
                    onClick = {
                        onVerPelicula()
                        onCambiarDialogo()
                    }
                ) {
                    Text("Ver")
                }
            },
            dismissButton = {
                Button(onClick = onCambiarDialogo) {
                    Text("No ver")
                }
            },
            onDismissRequest = onCambiarDialogo,
            icon = {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Ver pelicula")
            }

        )
    }

}