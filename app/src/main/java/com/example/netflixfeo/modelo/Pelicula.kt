package com.example.netflixfeo.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pelicula(
        @SerialName(value = "nombre")
        val nombre : String ,
        @SerialName(value = "director")
        val director : String ,
        @SerialName(value = "actorPrincipal")
        val actorPrincipal : String ,
        @SerialName(value = "duracionMinutos")
        val duracionMinutos : Int ,
        @SerialName(value = "caratula")
        val caratula : String ,

        )
