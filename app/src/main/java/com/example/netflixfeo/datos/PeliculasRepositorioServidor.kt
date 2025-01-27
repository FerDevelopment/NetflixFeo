package com.example.netflixfeo.datos

import com.example.netflixfeo.conexion.PelisApiServidorApi
import com.example.netflixfeo.modelo.Pelicula

interface PeliculasRepositorioServidor {
    suspend fun obtenerPeliculas(): List<Pelicula>
    //suspend fun actualizarPelicula(pelicula : Pelicula) : Pelicula
}

class ConexionPeliculasRepositorioServidor(
    private val repositorioServidorApi:
    PelisApiServidorApi
) : PeliculasRepositorioServidor {
    override suspend fun obtenerPeliculas(): List<Pelicula> = repositorioServidorApi.obtenerPelis()

    /*
     override suspend fun actualizarPelicula(pelicula : Pelicula) : Pelicula =
             repositorioServidorApi.actualizarPeli(pelicula)

     */
}
