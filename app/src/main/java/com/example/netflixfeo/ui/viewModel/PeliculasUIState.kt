package com.example.netflixfeo.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.netflixfeo.Peliculas
import com.example.netflixfeo.datos.PeliculasRepositorioServidor
import com.example.netflixfeo.datos.PuntuacionRepositorio
import com.example.netflixfeo.modelo.Pelicula
import com.example.netflixfeo.modelo.Puntuacion
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PeliculasUIState {
    data class ObtenerPelis(val pelis: List<Pelicula>) : PeliculasUIState

    /*
    data class ActualizarPeli(
            val pelicula : Pelicula , val id : String = pelicula.id
                             ) : PeliculasUIState

     */

    object Error : PeliculasUIState
    object Cargando : PeliculasUIState
}

sealed interface PuntuacionUIState {
    data class ObtenerExitoTodos(val puntuaciones: List<Puntuacion>) : PuntuacionUIState
    data class ObtenerExito(val puntuacion: Puntuacion) : PuntuacionUIState

    object CrearExito : PuntuacionUIState
    object ActualizarExito : PuntuacionUIState
    object Error : PuntuacionUIState
    object Cargando : PuntuacionUIState
}

class PeliculaViewModel(
    private val peliculasRepositorioServidor: PeliculasRepositorioServidor,
    private val puntuacionRepositorio: PuntuacionRepositorio
) :
    ViewModel() {
    var peliculasUIState: PeliculasUIState by mutableStateOf(PeliculasUIState.Cargando)
    var peliculaSelcionada: Pelicula by mutableStateOf(Pelicula("", "", "", 0, ""))

    var puntuacionUIState: PuntuacionUIState by mutableStateOf(PuntuacionUIState.Cargando)
        private set

    var puntuacionPeliPulsada: Puntuacion by mutableStateOf(
        Puntuacion(
            identificadorPeli = "",
            puntuacion = 0.0,
            vecesVistas = 0
        )
    )
        private set

    fun obtenerPelis() {
        viewModelScope.launch {
            peliculasUIState = PeliculasUIState.Cargando
            peliculasUIState = try {
                val listaPelis = peliculasRepositorioServidor.obtenerPeliculas()

                if (peliculaSelcionada.nombre.equals("")) {
                    peliculaSelcionada = listaPelis.get(0)
                }
                PeliculasUIState.ObtenerPelis(listaPelis)
            } catch (e: IOException) {
                PeliculasUIState.Error
            } catch (e: HttpException) {
                PeliculasUIState.Error
            }
        }
    }

    fun obtenerPuntuacion(pelicula: Pelicula) {
        viewModelScope.launch {
            puntuacionUIState = try {
                val puntuacion = puntuacionRepositorio.obtenerPuntuacion(pelicula.nombre)
                puntuacionPeliPulsada = puntuacion
                PuntuacionUIState.ObtenerExito(puntuacion)
            } catch (e: Exception) {
                val puntuacion = actualizarOSubirPuntuacion(
                    Puntuacion(
                        identificadorPeli = pelicula.nombre,
                        vecesVistas = 0,
                        puntuacion = 0.0
                    )
                )
                puntuacionPeliPulsada = puntuacion
                PuntuacionUIState.ObtenerExito(
                    puntuacion
                )


            }
        }
    }

    fun actualizarPuntosPuntuacion(puntos: Double) {
        viewModelScope.launch {
            puntuacionPeliPulsada = puntuacionPeliPulsada.copy(puntuacion = puntos)
            puntuacionRepositorio.actualizar(puntuacionPeliPulsada)
        }

    }

    private suspend fun actualizarOSubirPuntuacion(puntuacion: Puntuacion): Puntuacion {

        try {
            puntuacionRepositorio.actualizar(puntuacion)
            return puntuacion
        } catch (e: Exception) {

            puntuacionRepositorio.insertar(puntuacion)

            return puntuacion
        }


    }

    fun actualizarPeliculaPulsada(pelicula: Pelicula) {
        peliculaSelcionada = pelicula
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as Peliculas)
                val peliculaRepo = aplicacion.contenedor.peliculasRepositorioServidor
                val puntuacionRepo = aplicacion.contenedor.puntuacionRepositorio
                PeliculaViewModel(
                    peliculasRepositorioServidor = peliculaRepo,
                    puntuacionRepositorio = puntuacionRepo
                )
            }
        }

    }
}