package com.example.pico

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("PicoPreferences", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

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
                    Main(viewModel, isFirstLaunch) {
                        // 앱 최초 실행 여부 저장
                        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
                    }
                }
            }
        }
    }
}

@Composable
fun Main(viewModel: DailyTodoViewModel, isFirstLaunch: Boolean, onFirstLaunchComplete: () -> Unit) {
    val navController = rememberNavController()

    LaunchedEffect(isFirstLaunch) {
        if (!isFirstLaunch) {
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Scaffold { padding ->
        NavHost(
            navController = navController,
            startDestination = if (isFirstLaunch) "start1" else "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("start1") { StartScreen1(navController) }
            composable("start2") { StartScreen2(navController) }
            composable("start3") {
                StartScreen3(navController)
                // 랜딩 페이지 완료 시 호출
                LaunchedEffect(Unit) { onFirstLaunchComplete() }
            }
            composable("home") { HomeScreen(navController = navController, viewModel = viewModel) }
            composable("schedule") { ScheduleScreen(navController, viewModel) }
            composable("add") { AddScreen(navController = navController, viewModel = viewModel) }
            composable("my") { MyScreen(navController, viewModel) }
            composable("detail/{todoId}") { backStackEntry ->
                val todoId = backStackEntry.arguments?.getString("todoId")!!.toInt()
                DetailScreen(navController = navController, viewModel = viewModel, todoId = todoId)
            }
        }
    }
}
