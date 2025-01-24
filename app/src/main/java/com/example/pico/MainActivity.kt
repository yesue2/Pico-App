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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.ui.views.AddScreen
import com.example.pico.ui.views.DetailScreen
import com.example.pico.ui.views.HomeScreen
import com.example.pico.ui.views.MyScreen
import com.example.pico.ui.views.ScheduleScreen
import com.example.pico.ui.views.StartScreen1
import com.example.pico.ui.views.StartScreen2
import com.example.pico.ui.views.StartScreen3
import com.example.pico.viewmodel.DailyTodoViewModel
import com.example.pico.viewmodel.DailyTodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as TodoApp
        val viewModelFactory = DailyTodoViewModelFactory(app.repository)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(DailyTodoViewModel::class.java)

        setContent {
            PicoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(viewModel)
                }
            }
        }
    }
}

@Composable
fun Main(viewModel: DailyTodoViewModel) {
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
            composable("home") { HomeScreen(navController = navController, viewModel = viewModel) }
            composable("schedule") {
                ScheduleScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable("add") { AddScreen(navController = navController, viewModel = viewModel) }
            composable("my") { MyScreen(navController) }
            composable("detail/{todoId}") { backStackEntry ->
                val todoId = backStackEntry.arguments?.getString("todoId")!!.toInt()
                DetailScreen(navController = navController, viewModel = viewModel, todoId = todoId)
            }
        }
    }
}
