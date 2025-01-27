package com.example.netflixfeo.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val telefeno: String,
)