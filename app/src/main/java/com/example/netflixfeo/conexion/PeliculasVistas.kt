package com.example.netflixfeo.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.netflixfeo.dao.PeliculasVistasDao
import com.example.netflixfeo.modelo.PeliculaVista

@Database(entities = [PeliculaVista::class], version = 1, exportSchema = false)
abstract class PeliculaVistaBaseDatos : RoomDatabase() {

    abstract fun peliculasVistaDao(): PeliculasVistasDao

    companion object {
        @Volatile
        private var Instance: PeliculaVistaBaseDatos? = null

        fun obtenerBaseDatos(context: Context): PeliculaVistaBaseDatos {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PeliculaVistaBaseDatos::class.java,
                    "peliculasvistasbd"
                )
                    .build().also { Instance = it }
            }
        }
    }
}