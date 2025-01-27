package com.example.netflixfeo.datos

import com.example.netflixfeo.dao.PuntuacionDao
import com.example.netflixfeo.modelo.Puntuacion

interface PuntuacionRepositorio {
    suspend fun obtenerPuntuacion(id: String): Puntuacion
    suspend fun obtenerTodasLasPuntuaciones(): List<Puntuacion>
    suspend fun insertar(puntuacion: Puntuacion)
    suspend fun actualizar(puntuacion: Puntuacion)
    suspend fun eliminar(puntuacion: Puntuacion)
}

class ConexionPuntuacionRepositorio(
    private val inventarioDao: PuntuacionDao
) : PuntuacionRepositorio {
    override suspend fun obtenerPuntuacion(id: String): Puntuacion =
        inventarioDao.obtenerPuntuacion(id)

    override suspend fun obtenerTodasLasPuntuaciones(): List<Puntuacion> =
        inventarioDao.obtenerTodasLasPuntuaciones()

    override suspend fun insertar(puntuacion: Puntuacion) = inventarioDao.insertar(puntuacion)
    override suspend fun actualizar(puntuacion: Puntuacion) = inventarioDao.actualizar(puntuacion)
    override suspend fun eliminar(puntuacion: Puntuacion) = inventarioDao.eliminar(puntuacion)
}