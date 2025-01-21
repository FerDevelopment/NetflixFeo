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
import com.example.netflixfeo.modelo.Pelicula
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PeliculasUIState {
    data class ObtenerPelis(val pelis : List<Pelicula>) : PeliculasUIState

    data class ActualizarPeli(
            val pelicula : Pelicula , val id : String = pelicula.id
                             ) : PeliculasUIState

    object Error : PeliculasUIState
    object Cargando : PeliculasUIState
}

class PeliculaViewModel(private val peliculasRepositorioServidor : PeliculasRepositorioServidor) :
        ViewModel() {
    var peliculasUIState : PeliculasUIState by mutableStateOf(PeliculasUIState.Cargando)
    var peliculaSelcionada : Pelicula by mutableStateOf(Pelicula("" , "" , "" , 0 , "" , ""))

    fun obtenerPelis() {
        viewModelScope.launch {
            peliculasUIState = PeliculasUIState.Cargando
            peliculasUIState = try {
                val listaPelis = peliculasRepositorioServidor.obtenerPeliculas()

                if (peliculaSelcionada.nombre.equals("")) {
                    peliculaSelcionada = listaPelis.get(0)
                }
                PeliculasUIState.ObtenerPelis(listaPelis)
            }
            catch (e : IOException) {
                PeliculasUIState.Error
            }
            catch (e : HttpException) {
                PeliculasUIState.Error
            }
        }
    }

    fun actualizarPeliculaPulsada(pelicula : Pelicula) {
        peliculaSelcionada = pelicula
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as Peliculas)
                val peliculaRepo = aplicacion.contenedor.peliculasRepositorioServidor
                PeliculaViewModel(peliculasRepositorioServidor = peliculaRepo)
            }
        }

    }
}