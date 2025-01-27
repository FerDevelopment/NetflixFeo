package com.example.netflixfeo.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Puntuacion")
data class Puntuacion(
    @PrimaryKey(autoGenerate = false)
    val identificadorPeli: String,
    val vecesVistas: Int,
    val puntuacion: Double

)
