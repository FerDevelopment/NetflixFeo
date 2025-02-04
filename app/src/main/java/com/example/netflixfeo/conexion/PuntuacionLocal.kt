package com.example.netflixfeo.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.netflixfeo.dao.PeliculasVistasDao
import com.example.netflixfeo.dao.PuntuacionDao
import com.example.netflixfeo.modelo.PeliculaVista
import com.example.netflixfeo.modelo.Puntuacion

@Database(entities = [Puntuacion::class, PeliculaVista::class], version = 1, exportSchema = false)
abstract class PuntuacionBaseDatos : RoomDatabase() {

    abstract fun puntuacionDao(): PuntuacionDao
    abstract fun peliculaVistasDao(): PeliculasVistasDao

    companion object {
        @Volatile
        private var Instance: PuntuacionBaseDatos? = null

        fun obtenerBaseDatos(context: Context): PuntuacionBaseDatos {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PuntuacionBaseDatos::class.java, "puntuaciondb")
                    .build().also { Instance = it }
            }
        }

       
    }
}