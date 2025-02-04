package com.example.netflixfeo.datos

import android.content.Context
import com.example.netflixfeo.conexion.PelisApiServidorApi
import com.example.netflixfeo.conexion.PuntuacionBaseDatos
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp {
    val peliculasRepositorioServidor: PeliculasRepositorioServidor
    val puntuacionRepositorio: PuntuacionRepositorio
    val peliculasVistasRepositorio: PeliculasVistasRepositorio
}

class PelisContenedorApp(private val context: Context) : ContenedorApp {
    private val baseUrl = "https://basedatosandroid.onrender.com/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
    private val servicioRetrofit: PelisApiServidorApi by lazy {
        retrofit.create(PelisApiServidorApi::class.java)
    }
    override val peliculasRepositorioServidor: PeliculasRepositorioServidor by lazy {
        ConexionPeliculasRepositorioServidor(servicioRetrofit)
    }

    override val puntuacionRepositorio: PuntuacionRepositorio by lazy {
        ConexionPuntuacionRepositorio(PuntuacionBaseDatos.obtenerBaseDatos(context).puntuacionDao())
    }

    override val peliculasVistasRepositorio: PeliculasVistasRepositorio by lazy {
        ConexionPeliculasVistasRepositorio(
            PuntuacionBaseDatos.obtenerBaseDatos(context).peliculaVistasDao()
        )

    }
}