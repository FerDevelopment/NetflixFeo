package com.example.netflixfeo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.netflixfeo.modelo.Puntuacion

@Dao
interface PuntuacionDao {
    @Query("SELECT * from Puntuacion WHERE identificadorPeli = :id")
    suspend fun obtenerPuntuacion(id: String): Puntuacion

    @Query("SELECT * from Puntuacion ORDER BY identificadorPeli ASC")
    suspend fun obtenerTodasLasPuntuaciones(): List<Puntuacion>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(producto: Puntuacion)

    @Update
    suspend fun actualizar(producto: Puntuacion)

    @Delete
    suspend fun eliminar(producto: Puntuacion)
}