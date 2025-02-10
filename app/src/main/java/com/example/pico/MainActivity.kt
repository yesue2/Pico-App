package com.example.pico

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pico.ui.theme.PicoTheme
import com.example.pico.ui.views.AddScreen
import com.example.pico.ui.views.DetailScreen
import com.example.pico.ui.views.GoalDetailScreen
import com.example.pico.ui.views.HomeScreen
import com.example.pico.ui.views.MyScreen
import com.example.pico.ui.views.ScheduleScreen
import com.example.pico.ui.views.StartScreen1
import com.example.pico.ui.views.StartScreen2
import com.example.pico.ui.views.StartScreen3
import com.example.pico.viewmodel.DailyTodoViewModel
import com.example.pico.viewmodel.MonthlyGoalViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        setContent {
            PicoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(isFirstLaunch) {
                        // 앱 최초 실행 여부 저장
                        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
                    }
                }
            }
        }
    }
}

@Composable
fun Main(
    isFirstLaunch: Boolean,
    onFirstLaunchComplete: () -> Unit
) {
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
            composable("home") {
                HomeScreen(
                    navController = navController
                )
            }
            composable("schedule") { ScheduleScreen(navController) }
            composable("add") { AddScreen(navController = navController) }
            composable("my") { MyScreen(navController) }
            composable("detailTodo/{todoId}") { backStackEntry ->
                val todoId = backStackEntry.arguments?.getString("todoId")!!.toInt()
                DetailScreen(
                    navController = navController,
                    todoId = todoId
                )
            }
            composable("detailGoal/{goalId}") { backStackEntry ->
                val goalId = backStackEntry.arguments?.getString("goalId")!!.toInt()
                GoalDetailScreen(navController = navController, goalId = goalId)
            }
        }
    }
}
