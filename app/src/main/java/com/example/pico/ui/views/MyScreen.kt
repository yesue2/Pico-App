package com.example.pico.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pico.ui.components.BottomAppBar
import com.example.pico.ui.components.TaskList
import com.example.pico.ui.components.TopAppBar
import com.example.pico.viewmodel.DailyTodoViewModel

@Composable
fun MyScreen(navController: NavController, viewModel: DailyTodoViewModel) {
    Scaffold(
        topBar = { TopAppBar(screen = "My") },
        bottomBar = { BottomAppBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CompletedDailyTodoListSection(viewModel, navController)
            }

            item {
                AchievementChartSection()
            }
        }
    }
}

@Composable
fun CompletedDailyTodoListSection(viewModel: DailyTodoViewModel, navController: NavController) {
    val completedDailyTodos by viewModel.completedDailyTodos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "지난 할 일 목록",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        TaskList("한 주 동안 이렇게 멋지게 해냈어요! \uD83D\uDC4F", completedDailyTodos, navController)
    }
}
