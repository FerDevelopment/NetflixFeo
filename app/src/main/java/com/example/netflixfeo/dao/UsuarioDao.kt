package com.example.netflixfeo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.netflixfeo.modelo.Usuario

@Dao
interface UsuarioDao {

    @Query("SELECT * from Usuario WHERE id = :id")
    suspend fun obtenerUsuario(id: Int): Usuario


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(usuario: Usuario)

    @Update
    suspend fun actualizar(usuario: Usuario)

    @Delete
    suspend fun eliminar(usuario: Usuario)
}