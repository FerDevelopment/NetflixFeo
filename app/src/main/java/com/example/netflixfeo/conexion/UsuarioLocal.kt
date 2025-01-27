package com.example.netflixfeo.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.netflixfeo.dao.UsuarioDao

import com.example.netflixfeo.modelo.Usuario


@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class UsuarioBaseDatos : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var Instance: UsuarioBaseDatos? = null

        fun obtenerBaseDatos(context: Context): UsuarioBaseDatos {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UsuarioBaseDatos::class.java, "usuariodb").build()
                    .also { Instance = it }
            }
        }
    }
}