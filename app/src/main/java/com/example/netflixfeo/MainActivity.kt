package com.example.netflixfeo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.netflixfeo.ui.PeliculasApp
import com.example.netflixfeo.ui.theme.NetflixFeoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetflixFeoTheme {
                PeliculasApp()
            }
        }
    }
}



