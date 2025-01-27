package com.example.netflixfeo.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PeliculaVista")
data class PeliculaVista(
    @PrimaryKey(autoGenerate = false) val nombrePeli: String
)
