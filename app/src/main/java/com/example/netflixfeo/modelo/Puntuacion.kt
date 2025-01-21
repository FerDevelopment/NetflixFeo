package com.example.netflixfeo.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Puntuacion")
data class Puntuacion(
        @PrimaryKey(autoGenerate = true)
        val identificadorPeli : String ,
        val vecesVistas : Int ,
        )
