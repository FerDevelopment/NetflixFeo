package com.example.netflixfeo.ui.viewModel

import android.util.Log
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
import com.example.netflixfeo.datos.PeliculasVistasRepositorio
import com.example.netflixfeo.datos.PuntuacionRepositorio
import com.example.netflixfeo.modelo.Pelicula
import com.example.netflixfeo.modelo.PeliculaVista
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

    data object Error : PeliculasUIState
    data object Cargando : PeliculasUIState
}

sealed interface PuntuacionUIState {
    data class ObtenerExitoTodos(val puntuaciones: List<Puntuacion>) : PuntuacionUIState
    data class ObtenerExito(val puntuacion: Puntuacion) : PuntuacionUIState

    data object CrearExito : PuntuacionUIState
    data object ActualizarExito : PuntuacionUIState
    data object Error : PuntuacionUIState
    data object Cargando : PuntuacionUIState
}

sealed interface PeliculasVistasUIState {
    data class ObtenerExitoTodos(val peliculasVistas: List<PeliculaVista>) : PeliculasVistasUIState
    data class ObtenerExito(val peliculaVista: PeliculaVista) : PeliculasVistasUIState

    data object CrearExito : PeliculasVistasUIState
    data object ActualizarExito : PeliculasVistasUIState
    data object Error : PeliculasVistasUIState
    data object Cargando : PeliculasVistasUIState
}

class PeliculaViewModel(
    private val peliculasRepositorioServidor: PeliculasRepositorioServidor,
    private val puntuacionRepositorio: PuntuacionRepositorio,
    private val peliculasVistasRepositorio: PeliculasVistasRepositorio
) : ViewModel() {
    var peliculasUIState: PeliculasUIState by mutableStateOf(PeliculasUIState.Cargando)

    var puntuacionUIState: PuntuacionUIState by mutableStateOf(PuntuacionUIState.Cargando)
        private set

    var peliculaVistaUIState: PeliculasVistasUIState by mutableStateOf(PeliculasVistasUIState.Cargando)

    var peliculaSelcionada: Pelicula by mutableStateOf(Pelicula("", "", "", 0, ""))

    var peliculaSelcionadaVista: Pelicula by mutableStateOf(Pelicula("", "", "", 0, ""))

    var listaPelisVistas: List<PeliculaVista> = listOf()

    var listaPelis: List<Pelicula> = listOf()

    var puntuacionPeliPulsada: Puntuacion by mutableStateOf(
        Puntuacion(
            identificadorPeli = "", puntuacion = 0.0, vecesVistas = 0
        )
    )
        private set

    var peliculasVistas: MutableList<Pelicula> = mutableListOf()


    init {
        obtenerPelis()
        actualizarPelisVistas()
    }

    fun obtenerPelis() {
        viewModelScope.launch {
            peliculasUIState = PeliculasUIState.Cargando
            peliculasUIState = try {
                val listaPeliss = peliculasRepositorioServidor.obtenerPeliculas()
                listaPelis = listaPeliss
                if (peliculaSelcionada.nombre.equals("")) {
                    peliculaSelcionada = listaPelis.get(0)
                }
                PeliculasUIState.ObtenerPelis(listaPeliss)
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
                val puntuacion = Puntuacion(
                    identificadorPeli = pelicula.nombre, vecesVistas = 0, puntuacion = 0.0
                )
                puntuacionRepositorio.insertar(puntuacion)

                puntuacionPeliPulsada = puntuacion
                PuntuacionUIState.ObtenerExito(
                    puntuacion
                )


            }
        }
    }

    fun actualizarPeliculaVista(pelicula: Pelicula) {
        peliculaSelcionadaVista = pelicula
    }

    fun actualizarPuntosPuntuacion(puntos: Double) {
        puntuacionPeliPulsada = puntuacionPeliPulsada.copy(puntuacion = puntos)
        actualizarOSubirPuntuacion(puntuacionPeliPulsada)
    }

    fun actualizarOSubirPuntuacion(puntuacion: Puntuacion) {

        viewModelScope.launch {
            try {
                puntuacionRepositorio.actualizar(puntuacion)
                puntuacionPeliPulsada = puntuacion

            } catch (e: Exception) {

                puntuacionRepositorio.insertar(puntuacion)
                puntuacionPeliPulsada = puntuacion


            }
        }


    }

    fun actualizarListaPeliculasVistas() {
        viewModelScope.launch {
            peliculasVistas.clear()
            for (peliculaVista in listaPelisVistas) {
                for (pelicula in listaPelis) {
                    Log.e("A", "Paso por aqui")
                    if (pelicula.nombre.equals(peliculaVista.nombrePeli)) {
                        Log.e("B", "Me anyado")
                        peliculasVistas.add(pelicula)
                        break
                    }
                }
            }
            if (peliculaSelcionadaVista.nombre.equals("")) {
                try {
                    peliculaSelcionadaVista = peliculasVistas.get(0)
                } catch (e: Exception) {

                }

            }

        }


    }


    fun subirPeliculaVista() {

        viewModelScope.launch {

            if (peliculasVistasRepositorio.obtenerPeliculaVista(peliculaSelcionada.nombre) == 0) {
                peliculasVistasRepositorio.insertar(PeliculaVista(peliculaSelcionada.nombre))
            }

        }


    }

    fun actualizarPelisVistas() {
        viewModelScope.launch {

            obtenerPelisVistas()
            actualizarListaPeliculasVistas()

        }

    }

    private fun obtenerPelisVistas() {
        viewModelScope.launch {
            peliculaVistaUIState = PeliculasVistasUIState.Cargando
            peliculaVistaUIState = try {
                val listaPelis = peliculasVistasRepositorio.obtenerTodasLasPeliculasVistas()
                listaPelisVistas = listaPelis
                PeliculasVistasUIState.ObtenerExitoTodos(listaPelis)
            } catch (e: IOException) {
                PeliculasVistasUIState.Error
            } catch (e: HttpException) {
                PeliculasVistasUIState.Error
            }
        }
    }

    fun actualizarPeliculaPulsada(pelicula: Pelicula) {
        peliculaSelcionada = pelicula
    }

    fun actualizarVisualizacion() {

        puntuacionPeliPulsada = puntuacionPeliPulsada.copy(
            vecesVistas = puntuacionPeliPulsada.vecesVistas + 1
        )
        actualizarOSubirPuntuacion(puntuacionPeliPulsada)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as Peliculas)
                val peliculaRepo = aplicacion.contenedor.peliculasRepositorioServidor
                val puntuacionRepo = aplicacion.contenedor.puntuacionRepositorio
                val peliculasVistasRepo = aplicacion.contenedor.peliculasVistasRepositorio
                PeliculaViewModel(
                    peliculasRepositorioServidor = peliculaRepo,
                    puntuacionRepositorio = puntuacionRepo,
                    peliculasVistasRepositorio = peliculasVistasRepo

                )
            }
        }

    }
}