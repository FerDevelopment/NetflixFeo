package com.example.netflixfeo.ui

import android.graphics.Color.rgb
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.netflixfeo.R
import com.example.netflixfeo.modelo.Ruta
import com.example.netflixfeo.ui.componentes.MostrarAlertNoEstadistica
import com.example.netflixfeo.ui.componentes.VerPelicula
import com.example.netflixfeo.ui.pantallas.MostrarEstadisticasPelicula
import com.example.netflixfeo.ui.pantallas.MostrarPelis
import com.example.netflixfeo.ui.viewModel.PeliculaViewModel
import com.example.netflixfeo.ui.viewModel.PeliculasUIState
import com.example.netflixfeo.ui.viewModel.PeliculasVistasUIState
import com.example.netflixfeo.ui.viewModel.PuntuacionUIState

enum class Pantallas(
    @StringRes val titulo: Int
) {
    PantallaPelis(titulo = R.string.pelis), PantallaEstadisticas(titulo = R.string.estadisticas), PantallaPelisVistas(
        titulo = R.string.pelisVistas
    )
}

val listaRutas = listOf(
    Ruta(
        Pantallas.PantallaPelis.titulo,
        Pantallas.PantallaPelis.name,
        Icons.Filled.PlayArrow,
        Icons.Outlined.PlayArrow
    ), Ruta(
        Pantallas.PantallaPelisVistas.titulo,
        Pantallas.PantallaPelisVistas.name,
        Icons.Filled.Menu,
        Icons.Outlined.Menu
    )
)


@Composable
fun PeliculasApp(
    viewModel: PeliculaViewModel = viewModel(factory = PeliculaViewModel.Factory),
    navController: NavHostController = rememberNavController(),
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listaRutas.forEachIndexed { indice, ruta ->
                    NavigationBarItem(
                        icon = {
                            if (selectedItem == indice) FilledIconButton(
                                onClick = {
                                    selectedItem = indice
                                    navController.navigate(ruta.ruta)

                                }, colors = IconButtonColors(
                                    contentColor = Color(rgb(160, 71, 71)),
                                    disabledContentColor = Color.Yellow,
                                    disabledContainerColor = Color.Red,
                                    containerColor = Color(rgb(238, 223, 122))
                                )
                            ) {
                                Icon(
                                    imageVector = ruta.iconoLleno,
                                    contentDescription = stringResource(id = ruta.nombre),
                                )
                            }
                            else OutlinedIconButton(
                                onClick = {
                                    selectedItem = indice
                                    navController.navigate(ruta.ruta)
                                }, colors = IconButtonColors(
                                    contentColor = Color(rgb(160, 71, 71)),
                                    disabledContentColor = Color.Yellow,
                                    disabledContainerColor = Color.Red,
                                    containerColor = Color(rgb(238, 223, 122))
                                )
                            ) {
                                Icon(
                                    imageVector = ruta.iconoVacio,
                                    contentDescription = stringResource(id = ruta.nombre),
                                )
                            }
                        },
                        label = { Text(stringResource(id = ruta.nombre)) },
                        selected = selectedItem == indice,
                        onClick = {
                            selectedItem = indice
                            navController.navigate(ruta.ruta)
                        },
                        modifier = Modifier.background(Color.Transparent)
                    )
                }
            }
        }, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val peliculasVistasUIState = viewModel.peliculaVistaUIState
        val peliculasUIState = viewModel.peliculasUIState
        val puntuacionUIState = viewModel.puntuacionUIState
        var mostrarDialogo by remember { mutableStateOf(false) }
        NavHost(
            navController = navController,
            startDestination = Pantallas.PantallaPelis.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Grafo de las rutas
            composable(route = Pantallas.PantallaPelis.name) {
                /*Pantalla inicial*/

                when (peliculasUIState) {
                    is PeliculasUIState.ObtenerPelis -> {
                        MostrarPelis(peliculasUIState.pelis, onClickPeli = {
                            viewModel.actualizarPeliculaPulsada(it)
                        }, viewModel.peliculaSelcionada, onClickEstadistica = {
                            viewModel.obtenerPuntuacion(viewModel.peliculaSelcionada)
                            navController.navigate(Pantallas.PantallaEstadisticas.name)
                        }, onClickReproducir = {
                            mostrarDialogo = true
                        }, Dialogo = {
                            VerPelicula(onVerPelicula = {
                                viewModel.actualizarVisualizacion()
                                viewModel.subirPeliculaVista()
                                viewModel.actualizarPelisVistas()
                            }, onCambiarDialogo = {
                                mostrarDialogo = false
                            }, mostrarDialogo
                            )
                        }) {
                            viewModel.actualizarPelisVistas()
                        }
                    }

                    else -> {
                        viewModel.obtenerPelis()
                    }
                }
            }
            composable(route = Pantallas.PantallaEstadisticas.name) {
                when (puntuacionUIState) {
                    is PuntuacionUIState.ObtenerExito -> {
                        MostrarEstadisticasPelicula(peliculaSelcionada = viewModel.peliculaSelcionada,
                            puntuacionPeliPulsada = puntuacionUIState.puntuacion,
                            actualizarPuntosPuntuacion = {
                                viewModel.actualizarPuntosPuntuacion(it)
                            })
                    }

                    else -> {

                        MostrarAlertNoEstadistica(viewModel.peliculaSelcionada)
                    }
                }


            }
            composable(route = Pantallas.PantallaPelisVistas.name) {

                when (peliculasVistasUIState) {
                    is PeliculasVistasUIState.ObtenerExitoTodos -> {
                        MostrarPelis(
                            peliculas = viewModel.peliculasVistas,
                            onClickPeli = {
                                viewModel.actualizarPeliculaPulsada(it)
                                viewModel.actualizarPeliculaVista(it)
                            },
                            peliculaSelcionada = viewModel.peliculaSelcionadaVista,
                            onClickReproducir = {
                                mostrarDialogo = true
                            },
                            Dialogo = {
                                VerPelicula(onVerPelicula = {
                                    viewModel.actualizarVisualizacion()
                                }, onCambiarDialogo = {
                                    mostrarDialogo = false
                                }, mostrarDialogo
                                )
                            },
                            onClickEstadistica = {
                                viewModel.obtenerPuntuacion(viewModel.peliculaSelcionada)
                                navController.navigate(Pantallas.PantallaEstadisticas.name)
                            },
                            onPrimeraEntradaEjecutar = {
                                viewModel.actualizarPelisVistas()
                            }
                        )


                    }

                    is PeliculasVistasUIState.Cargando -> {
                        viewModel.actualizarPelisVistas()
                    }

                    else -> {
                        viewModel.actualizarPelisVistas()
                    }
                }

            }
        }
    }
}


