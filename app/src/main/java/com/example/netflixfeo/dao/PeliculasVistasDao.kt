package com.example.netflixfeo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.netflixfeo.modelo.PeliculaVista

@Dao
interface PeliculasVistasDao {

    @Query("SELECT Count(*) from PeliculaVista WHERE nombrePeli = :id")
    suspend fun obtenerPeliculaVista(id: String): Int

    @Query("SELECT * from PeliculaVista")
    suspend fun obtenerTodasLasPeliculasVistas(): List<PeliculaVista>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(peliculaVista: PeliculaVista)

    @Update
    suspend fun actualizar(peliculaVista: PeliculaVista)

    @Delete
    suspend fun eliminar(peliculaVista: PeliculaVista)
}