package com.example.netflixfeo

import android.app.Application
import com.example.netflixfeo.datos.ContenedorApp
import com.example.netflixfeo.datos.PelisContenedorApp

class Peliculas : Application() {
    lateinit var contenedor : ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = PelisContenedorApp(this)
    }
}