package com.example.netflixfeo.datos

import com.example.netflixfeo.dao.PeliculasVistasDao
import com.example.netflixfeo.modelo.PeliculaVista

interface PeliculasVistasRepositorio {
    suspend fun obtenerPeliculaVista(id: String): PeliculaVista
    suspend fun obtenerTodasLasPeliculasVistas(): List<PeliculaVista>
    suspend fun insertar(peliculaVista: PeliculaVista)
    suspend fun actualizar(peliculaVista: PeliculaVista)
    suspend fun eliminar(peliculaVista: PeliculaVista)
}

class ConexionPeliculasVistasRepositorio(
    private val peliculasVistasDao: PeliculasVistasDao
) : PeliculasVistasRepositorio {
    override suspend fun obtenerPeliculaVista(id: String): PeliculaVista =
        peliculasVistasDao.obtenerPeliculaVista(id)

    override suspend fun obtenerTodasLasPeliculasVistas(): List<PeliculaVista> =
        peliculasVistasDao.obtenerTodasLasPeliculasVistas()

    override suspend fun insertar(peliculaVista: PeliculaVista) = peliculasVistasDao.insertar(peliculaVista)
    override suspend fun actualizar(peliculaVista: PeliculaVista) = peliculasVistasDao.actualizar(peliculaVista)
    override suspend fun eliminar(peliculaVista: PeliculaVista) = peliculasVistasDao.eliminar(peliculaVista)
}