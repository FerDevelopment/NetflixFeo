package com.example.netflixfeo.datos

import com.example.netflixfeo.conexion.PelisApiServidorApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp {
    val peliculasRepositorioServidor : PeliculasRepositorioServidor
}

class PelisContenedorApp : ContenedorApp {
    private val baseUrl = "https://basedatosandroid.onrender.com/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl).build()
    private val servicioRetrofit : PelisApiServidorApi by lazy {
        retrofit.create(PelisApiServidorApi::class.java)
    }
    override val peliculasRepositorioServidor : PeliculasRepositorioServidor by lazy {
        ConexionPeliculasRepositorioServidor(servicioRetrofit)
    }
}