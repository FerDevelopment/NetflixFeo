package com.example.netflixfeo.conexion

import com.example.netflixfeo.modelo.Pelicula
import retrofit2.http.GET

interface PelisApiServidorApi {
    @GET("peliculas")
    suspend fun obtenerPelis(): List<Pelicula>
    /*
        @PUT("peliculas/{id}")
        suspend fun actualizarPeli(
                @Body
                pelicula : Pelicula ,
                @Path("id")
                id : String = pelicula.id
                                  ): Pelicula
                                  */

}