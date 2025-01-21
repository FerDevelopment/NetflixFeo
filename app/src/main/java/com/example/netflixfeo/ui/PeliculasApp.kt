package com.example.netflixfeo.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.netflixfeo.R
import com.example.netflixfeo.modelo.Ruta
import com.example.netflixfeo.ui.pantallas.MostrarPelis
import com.example.netflixfeo.ui.viewModel.PeliculaViewModel
import com.example.netflixfeo.ui.viewModel.PeliculasUIState

enum class Pantallas(
        @StringRes
        val titulo : Int
                    ) {
    PantallaPelis(titulo = R.string.pelis) ,

}

val listaRutas = listOf(
    Ruta(
        Pantallas.PantallaPelis.titulo ,
        Pantallas.PantallaPelis.name ,
        Icons.Filled.PlayArrow ,
        Icons.Outlined.PlayArrow
        )
    /* Ruta(
         Pantallas.PantallaFlores.titulo ,
         Pantallas.PantallaFlores.name ,
         Icons.Filled.ShoppingCart ,
         Icons.Outlined.ShoppingCart
         ) ,
     Ruta(
         Pantallas.PantallaClientes.titulo ,
         Pantallas.PantallaClientes.name ,
         Icons.Filled.AccountCircle ,
         Icons.Outlined.AccountCircle
         ) ,
     Ruta(
         Pantallas.PantallaEmpleado.titulo ,
         Pantallas.PantallaEmpleado.name ,
         Icons.Filled.Face ,
         Icons.Outlined.Face
         ) ,
     Ruta(
         Pantallas.PantallaFloresMarchitadas.titulo ,
         Pantallas.PantallaFloresMarchitadas.name ,
         Icons.Filled.Share ,
         Icons.Outlined.Share
         ) ,
     Ruta(
         Pantallas.PantallaTipoFlores.titulo ,
         Pantallas.PantallaTipoFlores.name ,
         Icons.Filled.Menu ,
         Icons.Outlined.Menu
         )*/
                       )

@Composable
fun PeliculasApp(
        viewModel : PeliculaViewModel = viewModel(factory = PeliculaViewModel.Factory) ,
        navController : NavHostController = rememberNavController() ,
                ) {
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listaRutas.forEachIndexed { indice , ruta ->
                    NavigationBarItem(
                        icon = {
                            if (selectedItem == indice)
                                Icon(
                                    imageVector = ruta.iconoLleno ,
                                    contentDescription = stringResource(id = ruta.nombre)
                                    )
                            else
                                Icon(
                                    imageVector = ruta.iconoVacio ,
                                    contentDescription = stringResource(id = ruta.nombre)
                                    )
                        } ,
                        label = { Text(stringResource(id = ruta.nombre)) } ,
                        selected = selectedItem == indice ,
                        onClick = {
                            selectedItem = indice
                            navController.navigate(ruta.ruta)
                        }
                                     )
                }
            }
        } ,
        modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
        val uiState = viewModel.peliculasUIState
        NavHost(
            navController = navController ,
            startDestination = Pantallas.PantallaPelis.name ,
            modifier = Modifier.padding(innerPadding)
               ) {
            // Grafo de las rutas
            composable(route = Pantallas.PantallaPelis.name) {
                /*Pantalla inicial*/

                when (uiState) {
                    is PeliculasUIState.ObtenerPelis -> {
                        MostrarPelis(
                            uiState.pelis ,
                            onClickPeli = {
                                viewModel
                                        .actualizarPeliculaPulsada(it)
                            } ,
                            viewModel.peliculaSelcionada

                            )
                    }

                    else -> {
                        viewModel.obtenerPelis()
                    }
                }
            }
        }
    }
}