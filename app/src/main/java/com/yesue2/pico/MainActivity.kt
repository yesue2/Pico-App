package com.yesue2.pico

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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yesue2.pico.ui.theme.PicoTheme
import com.yesue2.pico.ui.views.AddScreen
import com.yesue2.pico.ui.views.DetailScreen
import com.yesue2.pico.ui.views.GoalDetailScreen
import com.yesue2.pico.ui.views.HomeScreen
import com.yesue2.pico.ui.views.MyScreen
import com.yesue2.pico.ui.views.ScheduleScreen
import com.yesue2.pico.ui.views.StartScreen1
import com.yesue2.pico.ui.views.StartScreen2
import com.yesue2.pico.ui.views.StartScreen3
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
            val navController = rememberNavController()

            PicoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(isFirstLaunch, navController) {
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
    navController: NavController,
    onFirstLaunchComplete: () -> Unit
) {
    val navController = rememberNavController()

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
                // 첫 실행 완료 시만 실행되도록 변경
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
