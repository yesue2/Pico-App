package com.example.pico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.ui.views.AddScreen
import com.example.pico.ui.views.HomeScreen
import com.example.pico.ui.views.ScheduleScreen
import com.example.pico.ui.views.StartScreen1
import com.example.pico.ui.views.StartScreen2
import com.example.pico.ui.views.StartScreen3

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()

    Scaffold(
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "start1",
            modifier = Modifier.padding(padding)
        ) {
            composable("start1") { StartScreen1(navController) }
            composable("start2") { StartScreen2(navController) }
            composable("start3") { StartScreen3(navController) }
            composable("home") { HomeScreen(navController) }
            composable("schedule") { ScheduleScreen(navController) }
            composable("add") { AddScreen(navController) }
        }
    }
}
